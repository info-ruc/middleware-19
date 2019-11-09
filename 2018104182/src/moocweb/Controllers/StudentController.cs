using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Data.Entity;
using System.Web;
using System.Web.Mvc;
using System.Data;
using System.Threading.Tasks;
using System.Threading;
using System.Net;
using System.IO;
using moocweb.Models;
using moocweb.Filter;
using moocweb.Business;
using System.Data.Entity.Validation;
using Z.EntityFramework.Extensions;

namespace moocweb.Controllers
{
    [BasicAuth]
    public class StudentController : Controller
    {
        // GET: Student
        private Entities db = new Entities();
        private Teafunction tf = new Teafunction();
        protected override void Dispose(bool disposing) {
            if (disposing) {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
        [HttpPost]
        public ActionResult Edit_Account(stuinfo inpt) {
            long id = (long)Session["id"];
            var stu_info = db.stu_info.Find(id);
            stu_info.emailaddress = inpt.emailaddress;
            stu_info.gender = inpt.gender;
            stu_info.major = inpt.major;
            stu_info.name = inpt.name;
            stu_info.pass = inpt.pass;
            db.Entry(stu_info).State = EntityState.Modified;
            string res = "";
            try { db.SaveChanges(); }
            catch(DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        public JsonResult Practice_info() {
            long id = (long)Session["id"];
            var pracinfo = (from i in db.stuexam where i.stu_id == id && i.type == true select i).ToArray();
            var examinfo = (from i in db.stuexam where i.stu_id == id && i.type == false && i.end_time > DateTime.Now select i).ToArray();
            var examFinishinfo = (from i in db.stuexam where i.stu_id == id && i.type == false && i.end_time <= DateTime.Now select i).ToArray();
            var json = new {
                pracinfo,
                examinfo,
                examFinishinfo
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        public JsonResult Practice_example(long pqid) {
            var dbq = db.paper_ques.Find(pqid);
            var back = dbq.question.qbase_chapter.background;
            DataSet[] res = null;
            if (back.db == "Kingbase") {
                var kingbaseconn = new KingbaseConn();
                res = kingbaseconn.ShowBackGround(back.name);
                kingbaseconn.Dispose();
            } else if (back.db == "Mysql") {
                var mysqlconn = new MysqlConn();
                res = mysqlconn.ShowBackGround(back.name);
                mysqlconn.Dispose();
            } else if (back.db == "MSSQL") {
                var mssqlconn = new MSSQLConn();
                res = mssqlconn.ShowBackGround(back.name);
                mssqlconn.Dispose();
            } else {
                res = new DataSet[0];
            }
            int count = res.Count();
            var tname = new List<string>();
            var datas = new List<object[]>();
            string[][] colnames = new string[count][];
            for (int i = 0; i < count; i++) {
                var ds = res[i];
                var colcount = ds.Tables[0].Columns.Count;
                tname.Add(ds.DataSetName);
                colnames[i] = new string[colcount];
                int j = 0;
                foreach (DataColumn col in ds.Tables[0].Columns) {
                    colnames[i][j] = new string(col.ColumnName.ToCharArray());
                    //colnames[i][j] += $"({col.GetType().ToString()})";
                    j++;
                }
                var data = ds.Tables[0].Rows[0].ItemArray;
                datas.Add(data);
            }
            var json = new {
                count,
                tname,
                datas,
                colnames
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        public string Save_Ans(string ans, long pqid, bool judge) {
            ans = ans.Replace("###", "select");
            ans = ans.Replace("!!!", "delete");
            long id = (long)Session["id"];
            var spq = (from i in db.stu_paperques where i.paper_ques.id == pqid && i.stu_info.id == id select i).ToArray();
            if (spq.Count() == 0) {
                var inpt = new stu_paperques();
                inpt.ans = ans;
                inpt.paper_ques = db.paper_ques.Find(pqid);
                inpt.stu_info = db.stu_info.Find(id);
                inpt.state = judge;
                db.stu_paperques.Add(inpt);
            } else {
                if(spq[0].state == false) {
                    spq[0].ans = ans;
                    spq[0].state = judge;
                }
            }
            string res = "";
            try { db.SaveChanges(); }
            catch (Exception ex) {
                res = ex.InnerException.Message;
            }
            return res;
        }
        public JsonResult Sub_Ans(string ans, long pqid, long cost,string ecid) {
            ans = ans.Replace("\r\n", " ");
            ans = ans.Replace("###", "select");
            ans = ans.Replace("!!!", "delete");
            long id = (long)Session["id"];
            string account = (string)Session["account"];
            long ec_id = long.Parse(ecid);
            string username = (string)Session["account"];
            var dbq = db.paper_ques.Find(pqid);
            var q = dbq.question;
            var back = q.qbase_chapter.background;
            string back_name = back.name;
            var log = new stu_ans_log();
            var res = new Tuple<string, bool>("",false);
            if (back.db == "Kingbase") {
                KingbaseConn conn = new KingbaseConn();
                res = conn.JudgeSql(username, q, ans, back_name);
                conn.Dispose();
            }else if(back.db == "Mysql") {
                MysqlConn conn = new MysqlConn(username);
                res = conn.JudgeSql($"Student_{username}", q, ans, back_name);
                conn.Dispose();
            }else if(back.db == "MSSQL") {
                MSSQLConn conn = new MSSQLConn(username);
                res = conn.JudgeSql($"Student_{username}", q, ans, back_name);
                conn.Dispose();
            }
            log.ans = ans;
            log.paper_ques = dbq;
            log.stu_info = db.stu_info.Find(id);
            log.wronginfo = res.Item1;
            log.cost = new TimeSpan(cost);
            log.exam_class = db.exam_class.Find(ec_id);
            Save_Ans(ans, pqid,res.Item2);
            db.stu_ans_log.Add(log);
            db.SaveChanges();
            var json = new {
                message = res.Item1,
                judge = res.Item2
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        public async Task<ActionResult> Sub_Ans_Exam(long ecid,long[] pqid,string[] anss,long[] costs) {
            long id = (long)Session["id"];
            var examclass = db.exam_class.Find(ecid);
            var stuinfo = db.stu_info.Find(id);
            var logs = new List<stu_ans_log>();
            var ress = new List<Task<Tuple<double, string>>>();
            for(var i = 0; i < anss.Length; i++) {
                anss[i] = anss[i].Replace("###", "select");
                anss[i] = anss[i].Replace("!!!", "delete");
                var log = new stu_ans_log();
                log.ans = anss[i];
                log.paper_ques = db.paper_ques.Find(pqid[i]);
                log.exam_class = examclass;
                log.cost = TimeSpan.FromSeconds(costs[i]);
                var qid = log.paper_ques.question.id;
                var bid = log.paper_ques.question.qbase_chapter.background.id;
                log.stu_info = stuinfo;
                logs.Add(log);
                ress.Add(GetScoreAsync(qid,bid,log));
            }
            for(var i=0;i< anss.Length; i++) {
                var log = logs[i];
                var res = await ress[i];
                log.wronginfo = res.Item2;
                log.score = (decimal)res.Item1;
            }
            string except = "";
            db.stu_ans_log.AddRange(logs);
            try { db.SaveChanges(); }
            catch(DbEntityValidationException e) {
                except = tf.AnalyseError(e);
            }
            return Content(except);
        }
        public async Task<Tuple<double, string>> GetScoreAsync(long qid, long bid, stu_ans_log log) {
            /*var res = new Tuple<double, string>(0, "超时");
            var tsk = new Task(() =>
            {
                var question = db.question.Find(qid);
                var background = db.background.Find(bid);

                if (question.q_type <= 2) {
                    res = Judge_sql(question, background, log.ans);
                } else if (question.q_type >= 3 && question.q_type <= 5) {
                    res = new Tuple<double, string>(0, "答案错误");
                    if (question.right_answer == log.ans) {
                        res = new Tuple<double, string>(10, "congratulations!!");
                    }
                }   
            });
            var cts = new CancellationTokenSource();
            tsk.Start();
            cts.CancelAfter(2000);
            return res;*/
            return await Task.Run(() =>
            {
                var question = db.question.Find(qid);
                var background = db.background.Find(bid);
                var res = new Tuple<double, string>(0, "答案错误");
                if (question.q_type <= 2) {
                    res = Judge_sql(question, background, log.ans);
                } else if (question.q_type >= 3 && question.q_type <= 5) {
                    res = new Tuple<double, string>(0, "答案错误");
                    if (question.right_answer == log.ans) {
                        res = new Tuple<double, string>(10, "congratulations!!");
                    }
                }
                return res;
            });
        }
        public Tuple<double,string> Judge_sql(question q,background back,string ans) {
            string username = (string)Session["account"];
            ans = ans.ToUpper();
            if(ans == "") {
                return new Tuple<double, string>(0, "空字符");
            }
            if(ans.Last() == ';') {
                ans = ans.Remove(ans.Length - 1);
            }
            string back_name = back.name;
            var res = new Tuple<string, bool>("暂不支持该数据库", false);
            if (back.db == "Kingbase") {
                KingbaseConn conn = new KingbaseConn();
                res = conn.JudgeSql(username, q, ans, back_name);
                conn.Dispose();
            }else if(back.db == "Mysql") {
                MysqlConn conn = new MysqlConn(username);
                res = conn.JudgeSql($"Student_{username}", q, ans, back_name);
                conn.Dispose();
            } else if (back.db == "MSSQL") {
                MSSQLConn conn = new MSSQLConn(username);
                res = conn.JudgeSql($"Student_{username}", q, ans, back_name);
                conn.Dispose();
            }
            double score = 0;
            string exc = "";
            if (res.Item2 == true) {
                score = q.totvalue;
                exc = "congratulations!!";
                score = 10;
            } else {
                ans += "&exit";
                exc = res.Item1;
                var Dic = Server.MapPath("/flex");
                var path = Server.MapPath($"/flex/exe/question{q.id}.exe");
                score = double.Parse(tf.RunFlex(path, ans, Dic,username));
            }
            return new Tuple<double, string>(score, exc);
        }
        public ActionResult Refresh(long ecid, TimeSpan time_less) {
            long sid = (long)Session["id"];
            var stuinfo = db.stu_info.Find(sid);
            var examclass = db.exam_class.Find(ecid);
            timeless tl = db.timeless.FirstOrDefault(u => u.stu_info.id == stuinfo.id && u.exam_class.Id == ecid);
            tl.time = time_less;
            string except = "";
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                except = tf.AnalyseError(e);
            }
            ViewBag.rest_time = time_less;
            return Content(except);
        }
    }
}