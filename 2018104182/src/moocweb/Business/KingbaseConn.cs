using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data;
using moocweb.Models;
using KingbaseES.Data.Client;
using System.Threading;

namespace moocweb.Business
{
    public class KingbaseConn {
        public KingbaseESConnection conn;
        public Tuple<DataRow[],DataRow[]> DataTableCompare(DataTable t1, DataTable t2) {
            var result1 = t2.AsEnumerable().Take(50).Except(t1.AsEnumerable(),DataRowComparer.Default);
            var result2 = t1.AsEnumerable().Take(50).Except(t2.AsEnumerable(), DataRowComparer.Default);
            return Tuple.Create(result1.ToArray(), result2.ToArray());
        }
        public List<string> GetSchemaInfo(string back_name) {
            string sql = "select table_name from information_schema.tables where table_schema='" + back_name + "'";
            KingbaseESCommand cmd = new KingbaseESCommand(sql, conn);
            KingbaseESDataReader dr = null;
            dr = cmd.ExecuteReader();
            var tables = new List<string>();
            while (dr.Read()) {
                for (int i = 0; i < dr.FieldCount; i++) {
                    tables.Add((string)dr.GetValue(i));
                }
            }
            dr.Close();
            return tables;
        }
        public string CreateBackGround(background back) {
            var result = "";
            string[] sqls = back.sql.Split(';');
            try {
                KingbaseESCommand cmd = new KingbaseESCommand("CREATE SCHEMA " + '"' + back.name.ToUpper() + '"' + " " + sqls[0], conn);
                var res1 = cmd.ExecuteNonQuery();
                cmd.CommandText = "SET search_path TO " + '"' + back.name + '"' + ",public";
                cmd.ExecuteNonQuery();
                for (int i = 0; i < sqls.Count(); i++) {
                    cmd.CommandText = sqls[i];
                    cmd.ExecuteNonQuery();
                }
            }catch(Exception e) {
                result = e.Message;
            }
            return result;
        }
        public string CopyBack(string username, string back_name) {
            string res = $"{username}_{back_name}_{Thread.CurrentThread.ManagedThreadId}";
            KingbaseESCommand cmd = new KingbaseESCommand($"select table_name from information_schema.tables where table_schema='{res}'", conn);
            var reader = cmd.ExecuteReader();
            if (reader.Read()) {
                reader.Close();
                return res;//待复制数据库已存在副本，使用副本
            }
            reader.Close();
            var tables = GetSchemaInfo(back_name);
            cmd = new KingbaseESCommand($"CREATE SCHEMA \"{res}\" ",conn);
            cmd.ExecuteNonQuery();
            for (int i = 0; i < tables.Count(); i++) {
                cmd.CommandText = $"select * into \"{res}\".{tables[i]} FROM  (SELECT * FROM \"{back_name}\".{tables[i]})";
                cmd.ExecuteNonQuery();
            }
            return res;
        }
        public string DeleteBack(string back_name) {
            KingbaseESCommand cmd = new KingbaseESCommand($"DROP SCHEMA \"{back_name}\" CASCADE", conn);
            try { cmd.ExecuteNonQuery(); }
            catch(Exception e) {
                return e.Message;
            }
            return "";
        }
        public Tuple<string,bool> RunNonQueSql(string sql,string back_name,bool del) {
            string except = "";
            var nback_name = CopyBack("tmp", back_name);
            KingbaseESCommand cmd = new KingbaseESCommand("SET search_path TO " + '"' + nback_name + '"' + ",public", conn);
            cmd.ExecuteNonQuery();
            cmd.CommandText = sql;
            bool flag = true;
            try {
                cmd.ExecuteNonQuery();
            }
            catch(Exception e) {
                flag = false;
                except = e.Message;
            }
            var t = new Tuple<string, bool>(except, flag);
            if (del) {
                cmd.CommandText = $"DROP SCHEMA \"{nback_name}\" CASCADE";
                cmd.ExecuteNonQuery();
            }
            return t;
        }
        public Tuple<string,bool> RunSql(string sql,string back_name,bool del) {
            string except = "";
            var nback_name = CopyBack("tmp", back_name);
            KingbaseESCommand cmd = new KingbaseESCommand("SET search_path TO " + '"' + nback_name + '"' + ",public", conn);
            cmd.ExecuteNonQuery();
            cmd.CommandText = sql;
            DataSet res = new DataSet();
            KingbaseESDataAdapter resda;
            bool flag = true;
            try {
                resda = new KingbaseESDataAdapter(cmd);
                resda.Fill(res);
                var licount = res.Tables[0].Rows.Count;
                if(licount == 0) {
                    except = "null";
                } else {
                    foreach(var eli in res.Tables[0].Columns) {
                        except += eli.ToString() + " ";
                    }
                }
            }
            catch (System.Exception e) {
                flag = false;
                except = e.Message;
            }
            var t = new Tuple<string, bool>(except, flag);
            if (del) {
                cmd.CommandText = $"DROP SCHEMA \"{nback_name}\" CASCADE";
                cmd.ExecuteNonQuery();
            }
            return t;
        }
        public Tuple<string, bool> JudgeSql(string username, question ques, string ans, string back_name) {
            ans = ans.Trim();
            ans = ans.TrimEnd(';');
            string except = null;
            var nback_name = CopyBack(username, back_name);
            bool iscorrect = false;
            KingbaseESCommand cmd = new KingbaseESCommand("SET search_path TO " + '"' + nback_name + '"' + ",public", conn);
            cmd.ExecuteNonQuery();
            cmd.CommandText = ques.right_answer;
            DataSet right_ds = new DataSet();
            if (ques.q_type == 1) {
                KingbaseESDataAdapter da = new KingbaseESDataAdapter(cmd);
                da.Fill(right_ds);
                da.Dispose();
            }else if(ques.q_type == 2) {
                cmd.ExecuteNonQuery();
                cmd.CommandText = ques.check_sentence;
                KingbaseESDataAdapter da = new KingbaseESDataAdapter(cmd);
                da.Fill(right_ds);
                da.Dispose();
                DeleteBack(nback_name);
                nback_name = CopyBack(username, back_name);
            }
            cmd.CommandText = ans;
            cmd.CommandTimeout = 5;
            DataTable ans_dt = new DataTable();
            if(ques.q_type == 2) {
                try { cmd.ExecuteNonQuery(); }
                catch (System.Exception e) {
                    except = e.Message;
                    iscorrect = false;
                    cmd.CommandText = $"DROP SCHEMA \"{nback_name}\" CASCADE";
                    cmd.ExecuteNonQuery();
                    return new Tuple<string, bool>(except, iscorrect);
                }
                cmd.CommandText = ques.check_sentence;
            } else {
                cmd.CommandText = ans + " limit 100";
            }
            //KingbaseESDataReader dr = null;
            int count = 0;
            try {
                /*
                dr = cmd.ExecuteReader();
                for (var i = 0; i < dr.FieldCount; i++) {
                    DataColumn column = new DataColumn();
                    column.DataType = dr.GetFieldType(i);
                    column.ColumnName = dr.GetName(i);
                    ans_dt.Columns.Add(column);
                }
                while (dr.Read() && count<=60) {
                    DataRow row = ans_dt.NewRow();
                    for(var i = 0; i < dr.FieldCount; i++) {
                        row[i] = dr[i].ToString();
                    }
                    ans_dt.Rows.Add(row);
                    row = null;
                    count++;
                }
                dr.Close();
                */
                KingbaseESDataAdapter da = new KingbaseESDataAdapter(cmd);
                da.Fill(0,100,ans_dt);
                da.Dispose();
                var res = DataTableCompare(ans_dt, right_ds.Tables[0]);
                if (res.Item1.Length > 0 || res.Item2.Length > 0) {
                    iscorrect = false;
                    if(ques.q_type == 2) {
                        except += $"验证语句{ques.check_sentence}下\n";
                    }
                    if(res.Item1.Length > 0) {
                        var str = "";
                        foreach(var item in res.Item1[0].ItemArray) {
                            str += item.ToString().TrimEnd(' ') + ' ';
                        }
                        except += $"比正确结果少{res.Item1.Length}个，如:{str}\n";
                    }
                    if (res.Item2.Length > 0) {
                        var str = "";
                        foreach (var item in res.Item2[0].ItemArray) {
                            str += item.ToString().TrimEnd(' ') + ' ';
                        }
                        except += $"比正确结果多{res.Item2.Length}个，如:{str}\n";
                    }
                } else {
                    iscorrect = true;
                    except = "congratulations!!";
                }
            }
            catch (System.Exception e) {
                except = e.Message;
                iscorrect = false;
            }
            cmd.CommandText = $"DROP SCHEMA \"{nback_name}\" CASCADE";
            try { cmd.ExecuteNonQuery(); }
            catch (System.Exception e) {
                except = "执行超时";
                iscorrect = false;
            }
            var re = new Tuple<string, bool>(except, iscorrect);
            return re;
        }
        public DataSet[] ShowBackGround(string back_name) {
            KingbaseESCommand cmd = new KingbaseESCommand();
            cmd.Connection = conn;
            var tables = GetSchemaInfo(back_name);
            DataSet[] data = new DataSet[tables.Count()];
            for (int i = 0; i < tables.Count(); i++) {
                cmd.CommandText = "select * from " + '"' + back_name + '"' + "." + tables[i];
                KingbaseESDataAdapter da = new KingbaseESDataAdapter(cmd);
                DataSet ds = new DataSet();
                da.Fill(ds);
                ds.DataSetName = tables[i];
                data[i] = ds;
                da.Dispose();
            }
            return data;
        }
        //public void TestDQL(question ques, string ans);
        public Tuple<string, object> InsertData(string path, char deli, string back_name, int table_num, string filename) {
            string sql = "select table_name from information_schema.tables where table_schema='" + back_name + "'";
            KingbaseESCommand cmd = new KingbaseESCommand(sql, conn);
            KingbaseESDataReader dr = null;
            dr = cmd.ExecuteReader();
            var tables = new List<string>();
            while (dr.Read()) {
                for (int i = 0; i < dr.FieldCount; i++) {
                    tables.Add((string)dr.GetValue(i));
                }
            }
            dr.Close();
            string table_name = $"{back_name}.{tables[table_num]}";
            path = $"'{path.Replace(@"\","/")}'";
            string[] lines = { $"DELIMITER='{deli}'", "TYPE=csv", $"DATAFILE={path}", $"TABLE={table_name}", "ROLLBACK=True" };
            var fname = filename.Split('.')[0];
            var ctrl_path = HttpContext.Current.Server.MapPath($"/KingbaseCtrl/{fname + ".ctrl"}");
            System.IO.File.WriteAllLines(ctrl_path, lines);
            sql = $"select bulkload('{ctrl_path}')";
            cmd.CommandText = sql;
            string except = "";
            object result = new object();
            try { result = cmd.ExecuteScalar(); }
            catch(System.Exception e) {
                except = e.Message;
            }
            return new Tuple<string, object>(except, result);
        }
        public string ClearTable(string back_name,int table_num) {
            string sql = "select table_name from information_schema.tables where table_schema='" + back_name + "'";
            KingbaseESCommand cmd = new KingbaseESCommand(sql, conn);
            KingbaseESDataReader dr = null;
            dr = cmd.ExecuteReader();
            var tables = new List<string>();
            while (dr.Read()) {
                for (int i = 0; i < dr.FieldCount; i++) {
                    tables.Add((string)dr.GetValue(i));
                }
            }
            dr.Close();
            back_name = '"' + back_name + '"';
            string table_name = $"{back_name}.{tables[table_num]}";
            sql = $"delete from {table_name}";
            string except = "";
            try{ cmd.ExecuteNonQuery(); }
            catch (System.Exception e) {
                except = e.Message;
            }
            return except;
        }
        public KingbaseConn() {
            string connstring = "Server=localhost;User Id=SYSTEM;Password=!qaz@wsx3edc;Database=WEBMOOC;";
            conn = new KingbaseESConnection(connstring);
            conn.Open();
        }
        public void Dispose() {
            conn.Close();
        }
    }
}