using System;
using System.Collections.Generic;
using System.Linq;
using System.Data.Entity;
using System.Data;
using moocweb.Models;
using MySql.Data.MySqlClient;
using System.Threading;

namespace moocweb.Business
{
    public class MysqlConn
    {
        // GET: Mysql
        private MySqlConnection Conn = new MySqlConnection("server=127.0.0.1;port=3306;user=AddBack;password=123456;Database=moocback;Charset=utf8");
        public MysqlConn() {
            Conn.Open();
        }
        public MysqlConn(string user) {
            Conn.ConnectionString = $"server=127.0.0.1;port=3306;user=Student;password=;Database=moocback;Charset=utf8;default command timeout=0";
            Conn.Open();
        }
        public void Dispose() {
            Conn.Close();
        }
        public Tuple<DataRow[], DataRow[]> DataTableCompare(DataTable t1, DataTable t2) {
            var result1 = t2.AsEnumerable().Take(50).Except(t1.AsEnumerable(), DataRowComparer.Default);
            var result2 = t1.AsEnumerable().Take(50).Except(t2.AsEnumerable(), DataRowComparer.Default);
            return Tuple.Create(result1.ToArray(), result2.ToArray());
        }
        public string AddBack(string backname,string sqltext)
        {
            var res = "";
            sqltext = sqltext.Trim();
            sqltext = sqltext.TrimEnd(';');
            String[] sqls = sqltext.Split(';');
            var cmd = new MySqlCommand($"create database {backname} character set = utf8", Conn);
            try{
                cmd.ExecuteNonQuery();
                cmd.CommandText = $"use {backname}";
                cmd.ExecuteNonQuery();
                for (int i = 0; i < sqls.Count(); i++) {
                    cmd.CommandText = sqls[i];
                    cmd.ExecuteNonQuery();
                }
            }
            catch(Exception e) {
                res = e.Message;
            }
            return res;
        }
        public List<string> GetSchemaInfo(string back_name) {
            string sql = $"select table_name from information_schema.tables where table_schema='{back_name}'";
            var cmd = new MySqlCommand(sql, Conn);
            MySqlDataReader dr = cmd.ExecuteReader();
            var tables = new List<string>();
            while (dr.Read()) {
                for (int i = 0; i < dr.FieldCount; i++) {
                    tables.Add((string)dr.GetValue(i));
                }
            }
            dr.Close();
            return tables;
        }
        public DataSet[] ShowBackGround(string back_name) {
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = Conn;
            cmd.CommandText = $"use {back_name}";
            cmd.ExecuteNonQuery();
            var tables = GetSchemaInfo(back_name);
            DataSet[] data = new DataSet[tables.Count()];
            for (int i = 0; i < tables.Count(); i++) {
                cmd.CommandText = $"select * from {tables[i]}";
                MySqlDataAdapter da = new MySqlDataAdapter(cmd);
                DataSet ds = new DataSet();
                da.Fill(ds);
                ds.DataSetName = tables[i];
                data[i] = ds;
                da.Dispose();
            }
            return data;
        }
        public string CopyBack(string username, string back_name) {
            string res = $"{username}_{back_name}_{Thread.CurrentThread.ManagedThreadId}";
            var cmd = new MySqlCommand($"select * from information_schema.schemata where schema_name='{res}'", Conn);
            var reader = cmd.ExecuteReader();
            if (reader.Read()) {
                reader.Close();
                return res;//待复制数据库已存在副本，使用副本
            }
            reader.Close();
            var tables = GetSchemaInfo(back_name);
            cmd.CommandText = $"CREATE DATABASE {res} DEFAULT CHARACTER SET utf8";
            cmd.ExecuteNonQuery();
            for (int i = 0; i < tables.Count(); i++) {
                cmd.CommandText = $"create table {res}.{tables[i]} (SELECT * FROM {back_name}.{tables[i]})";
                cmd.ExecuteNonQuery();
            }
            return res;
        }
        public string DeleteBack(string back_name) {
            var cmd = new MySqlCommand($"DROP DATABASE {back_name}", Conn);
            try { cmd.ExecuteNonQuery(); }
            catch (Exception e) {
                return e.Message;
            }
            return "";
        }
        public string Insertdata(string filepath, char deli, string back_name, string table_name, string character) {
            var nfilepath = filepath.Replace("\\", "//");
            string command = $"load data infile '{nfilepath}' replace into table {back_name}.{table_name} character set {character} fields terminated by '{deli}' ";
            var cmd = new MySqlCommand(command, Conn);
            var res = "";
            try { cmd.ExecuteNonQuery(); }
            catch(Exception e) {
                res = e.Message;
            };
            return res;
        }
        public string Cleartable(string back_name, string table_name) {
            string command = $"delete from {back_name}.{table_name}";
            var cmd = new MySqlCommand(command, Conn);
            var res = "";
            try { cmd.ExecuteNonQuery(); }
            catch (Exception e) {
                res = e.Message;
            };
            return res;
        }
        public Tuple<DataTable,string,bool> RunSql(string user_name,string sql, string back_name, bool isNonQue,bool del) {
            string[] ss = sql.Split(new string[] { "\n" }, StringSplitOptions.None);
            if (!isNonQue) {
                for (int i = 0; i < ss.Length; i++) {
                    ss[i] = ss[i].Trim();
                    ss[i] = ss[i].TrimEnd(';');
                    ss[i] += " limit 100;";
                }
            }
            sql = string.Join("\n", ss);
            string except = "";
            DataTable resdata = new DataTable();
            bool flag = false;
            var nback = CopyBack(user_name, back_name);
            var cmd = new MySqlCommand($"use {nback}",Conn);
            //var cmd = new MySqlCommand(sql, Conn);
            cmd.CommandTimeout = 3;
            
            try { cmd.ExecuteNonQuery(); }
            catch(Exception e) {
                except = e.Message;
                return new Tuple<DataTable, string, bool>(resdata, except, flag);
            }
            //cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = sql;
            MySqlDataAdapter adapt;
            try {
                if (!isNonQue) {
                    adapt = new MySqlDataAdapter(cmd);
                    adapt.Fill(resdata);
                }else {
                    cmd.ExecuteNonQuery();
                }
            }catch(Exception e) {
                except = e.Message;
                if(except == "Fatal error encountered during command execution.") {
                    except = "执行超时";
                    cmd.Connection.Open();
                }
                cmd.CommandText = $"DROP DATABASE {nback}";
                cmd.ExecuteNonQuery();
                return new Tuple<DataTable, string, bool>(resdata, except, flag);
            }
            if (del || !isNonQue) {
                cmd.CommandText = $"DROP DATABASE {nback}";
                cmd.ExecuteNonQuery();
            }
            flag = true;
            return new Tuple<DataTable, string, bool>(resdata, except, flag);
        }
        public Tuple<string,bool> JudgeSql(string username, question ques, string ans, string back_name) {
            string except = "";
            bool iscorrect = false;
            bool isNonQue = true;
            if(ques.q_type == 1) {
                isNonQue = false;
            }else if(ques.q_type == 2) {
                isNonQue = true;
            }
            var ansres = RunSql(username, ans, back_name, isNonQue, false);
            if (!ansres.Item3) {//语句执行出错
                iscorrect = false;
                except = ansres.Item2;
                return new Tuple<string, bool>(except, iscorrect);
            }
            if (isNonQue) {//执行检测语句，得出结果并删除副本数据库
                ansres = RunSql(username, ques.check_sentence, back_name, false, true);
            }
            var rightans = RunSql(username, ques.right_answer, back_name, isNonQue, false);
            if (isNonQue) {
                rightans = RunSql(username, ques.check_sentence, back_name, false, true);
            }
            var res = DataTableCompare(ansres.Item1, rightans.Item1);
            if (res.Item1.Length > 0 || res.Item2.Length > 0) {
                iscorrect = false;
                if (isNonQue == true) {
                    except += $"验证语句{ques.check_sentence}下\n";
                }
                if (res.Item1.Length > 0) {
                    var str = "";
                    foreach (var item in res.Item1[0].ItemArray) {
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
                var DifInOrder = Equals(ansres.Item1.Rows[0].ItemArray[0], rightans.Item1.Rows[0].ItemArray[0]);
                if (!DifInOrder) {
                    except = $"输出行序有误";
                } else {
                    iscorrect = true;
                    except = "congratulations!!";
                }
            }
            var re = new Tuple<string, bool>(except, iscorrect);
            return re;
        }
    }
}