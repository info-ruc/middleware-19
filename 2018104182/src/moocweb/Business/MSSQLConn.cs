using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Threading;
using moocweb.Models;

namespace moocweb.Business {
    public class MSSQLConn 
    {
        private SqlConnection Conn = new SqlConnection("server=(local);Initial Catalog=moocweb;user=Teacher;password=");
        public MSSQLConn() {
            Conn.Open();
        }
        public MSSQLConn(string user) {
            Conn.ConnectionString = "server=(local);Initial Catalog=moocweb;user=Teacher;password=";
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
        public string AddBack(string backname,string sqltext) {
            var res = "";
            sqltext = sqltext.Trim();
            sqltext = sqltext.TrimEnd(';');
            String[] sqls = sqltext.Split(';');
            var cmd = new SqlCommand($"create database {backname}", Conn);
            try {
                cmd.ExecuteNonQuery();
                cmd.CommandText = $"use {backname}";
                cmd.ExecuteNonQuery();
                for (int i = 0; i < sqls.Count(); i++) {
                    cmd.CommandText = sqls[i];
                    cmd.ExecuteNonQuery();
                }
            }
            catch (Exception e) {
                res = e.Message;
            }
            return res;
        }
        public List<string> GetSchemaInfo(string back_name) {
            string sql = $"use {back_name} select name from sys.tables";
            var cmd = new SqlCommand(sql, Conn);
            SqlDataReader dr = cmd.ExecuteReader();
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
            SqlCommand cmd = new SqlCommand();
            cmd.Connection = Conn;
            cmd.CommandText = $"use {back_name}";
            cmd.ExecuteNonQuery();
            var tables = GetSchemaInfo(back_name);
            DataSet[] data = new DataSet[tables.Count()];
            for (int i = 0; i < tables.Count(); i++) {
                cmd.CommandText = $"select * from {tables[i]}";
                SqlDataAdapter da = new SqlDataAdapter(cmd);
                DataSet ds = new DataSet();
                da.Fill(ds);
                ds.DataSetName = tables[i];
                data[i] = ds;
                da.Dispose();
            }
            return data;
        }
        public string CopyBack(string username, string back_name) {
            var res = $"{username}_{back_name}_{Thread.CurrentThread.ManagedThreadId}";
            var cmd = new SqlCommand($"SELECT Name FROM Master..SysDatabases where Name='{res}'", Conn);
            var reader = cmd.ExecuteReader();
            if (reader.Read()) {
                reader.Close();
                return res;//待复制数据库已存在副本，使用副本
            }
            reader.Close();
            var tables = GetSchemaInfo(back_name);
            cmd.CommandText = $"CREATE DATABASE {res}";
            cmd.ExecuteNonQuery();
            for (int i = 0; i < tables.Count(); i++) {
                cmd.CommandText = $"select * into {res}..{tables[i]} FROM {back_name}..{tables[i]}";
                cmd.ExecuteNonQuery();
            }
            return res;
        }
        public string Insertdata(string filepath, char deli, string back_name, string table_name, string character) {
            var nfilepath = filepath.Replace("\\", "//");
            string command = $"bulk insert {back_name}..{table_name} FROM '{filepath}' with(fieldterminator='{deli}',rowterminator='\n')";
            var cmd = new SqlCommand(command, Conn);
            var res = "";
            try { cmd.ExecuteNonQuery(); }
            catch (Exception e) {
                res = e.Message;
            };
            return res;
        }
        public string Cleartable(string back_name, string table_name) {
            string command = $"delete from {back_name}..{table_name}";
            var cmd = new SqlCommand(command, Conn);
            var res = "";
            try { cmd.ExecuteNonQuery(); }
            catch (Exception e) {
                res = e.Message;
            };
            return res;
        }
        public Tuple<DataTable, string, bool> RunSql(string user_name, string sql, string back_name, bool isNonQue, bool del) {
            if (!isNonQue) {
                sql = sql.Trim();
                sql = sql.TrimEnd(';');
            }
            string except = "";
            DataTable resdata = new DataTable();
            bool flag = false;
            var nback = CopyBack(user_name, back_name);
            var cmd = new SqlCommand($"use {nback}", Conn);
            try { cmd.ExecuteNonQuery(); }
            catch (Exception e) {
                except = e.Message;
                return new Tuple<DataTable, string, bool>(resdata, except, flag);
            }
            cmd.CommandText = sql;
            SqlDataAdapter adapt;
            try {
                if (!isNonQue) {
                    adapt = new SqlDataAdapter(cmd);
                    adapt.Fill(resdata);
                } else {
                    cmd.ExecuteNonQuery();
                }
            }
            catch (Exception e) {
                except = e.Message;
                cmd.CommandText = "use moocweb";
                cmd.ExecuteNonQuery();
                cmd.CommandText = $"DROP DATABASE {nback}";
                cmd.ExecuteNonQuery();
                return new Tuple<DataTable, string, bool>(resdata, except, flag);
            }
            if (del || !isNonQue) {
                cmd.CommandText = "use moocweb";
                cmd.ExecuteNonQuery();
                cmd.CommandText = $"DROP DATABASE {nback}";
                cmd.ExecuteNonQuery();
            }
            flag = true;
            return new Tuple<DataTable, string, bool>(resdata, except, flag);
        }
        public Tuple<string, bool> JudgeSql(string username, question ques, string ans, string back_name) {
            string except = "";
            bool iscorrect = false;
            bool isNonQue = true;
            if (ques.q_type == 1) {
                isNonQue = false;
            } else if (ques.q_type == 2) {
                isNonQue = true;
            }
            //不删除副本的语句执行
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