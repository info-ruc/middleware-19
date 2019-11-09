using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Threading.Tasks;
using moocweb.Models;
using moocweb.Filter;
using moocweb.Business;
namespace moocweb.Controllers
{
    [BasicAuth]
    public class StuhomeController : Controller
    {
        // GET: Stuhome
        private Entities db = new Entities();
        protected override void Dispose(bool disposing) {//默认析构
            if (disposing) {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
        public ActionResult Index(long id)
        {
            return RedirectToAction("Account");
        }
        public ActionResult Exit() {
            var id = (long)Session["id"];
            var student = db.stu_info.Find(id);
            student.lastTime = DateTime.Now;
            db.Entry(student).State = System.Data.Entity.EntityState.Modified;
            db.SaveChanges();
            Session.Abandon();
            return RedirectToAction("Login", "Login");
        }
        public ActionResult Account() {            
            long id = (long)Session["id"];
            var stuinfo = db.stuinfo.Find(id);
            var soptions = new List<SelectListItem>();
            soptions.Add(new SelectListItem { Text = "男", Value = "true" });
            soptions.Add(new SelectListItem { Text = "女", Value = "false" });
            if(stuinfo.gender == null || stuinfo.gender == true) {
                ViewBag.Soptions = new SelectList(soptions, "Value", "Text");
            }else {
                soptions.Reverse();
                ViewBag.Soptions = new SelectList(soptions, "Value", "Text");
            }
            return View(stuinfo);
        }
        public ActionResult Practice(long id) {
            var exam = db.exam_class.Find(id).exam;
            var stuid = (long)Session["id"];
            ViewBag.id = id;
            ViewBag.name = exam.name;
            var quesli = (from st in db.stupaperques where st.eid == exam.Id && st.stu_id == stuid select st).ToArray();
            return View(quesli);
        }
        public ActionResult Practice_info(long id) {
            ViewBag.ecid = id;
            var examclass = db.exam_class.Find(id);
            var exam = examclass.exam;
            ViewBag.time = exam.start_time.ToString("yyyy/MM/dd") + " - " + exam.end_time.ToString("yyyy/MM/dd");
            var duration = exam.test_time.ToString();
            string[] time = duration.Split(':');
            string[] chinese = { "小时", "分钟", "秒" };
            for (int i = 0; i < time.Length; i++)
                if (time[i] == "00")
                    time[i] = "";
                else
                    time[i] = int.Parse(time[i]).ToString() + chinese[i];
            ViewBag.duration = string.Join("", time);
            ViewBag.score = 100;
            ViewBag.attention = exam.attention;
            ViewBag.details = exam.details;
            return View();
        }
        public ActionResult Exam(long id) {
            var examclass = db.exam_class.Find(id);
            var exam = examclass.exam;
            var stuid = (long)Session["id"];
            var stuinfo = db.stu_info.Find(stuid);
            Session.Timeout = 150;
            var logs = from i in db.stu_ans_log where i.stu_info.id == stuid && i.exam_class.Id == id select i;
            if (logs.Count() > 0) {
                ViewBag.isfinish = true;
                ViewBag.answerli = (from i in logs select i.ans).ToArray();
                ViewBag.scoreli = (from i in logs select i.score).ToArray();
            }
            ViewBag.ecid = id;
            ViewBag.name = exam.name;
            var test_time = (from i in db.timeless where i.stu_info.id == stuid && i.exam_class.Id == id select i.time).ToList();
            var paper = new paper();
            if (test_time.Count == 0) {
                var x = new timeless();
                x.exam_class = examclass;
                x.stu_info = stuinfo;
                x.time = exam.test_time;
                db.timeless.Add(x);
                db.SaveChanges();
            }
            var test_time2 = (from i in db.timeless where i.stu_info.id == stuid && i.exam_class.Id == id select i.time).ToList();
            ViewBag.rest_time = test_time2[0];
            var quesli = (from st in db.stupaperques where st.eid == exam.Id && st.stu_id == stuid select st).ToArray();
            return View(quesli);
        }
        public ActionResult Exam_feedback(long id) {
            var examclass = db.exam_class.Find(id);
            ViewBag.ecid = id;
            var exam = examclass.exam;
            var stuid = (long)Session["id"];
            var stuinfo = db.stu_info.Find(stuid);

            var data = from i in db.examfeedback where i.ec_id == id && i.stu_id == stuid select i;
            var stuli = (from i in data select i.score).ToArray();
            decimal? sum = 0;
            for (var i = 0; i < stuli.Length; i++) {
                if (stuli[i] == null) {
                    stuli[i] = 0;
                }
                sum += stuli[i];
            }
            var costli = (from i in data select i.cost).ToArray();
            var costs = new List<string>();
            for (var i = 0; i < costli.Length; i++) {
                if (costli[i] == null) {
                    costli[i] = TimeSpan.FromSeconds(0);
                }
                costs.Add(costli[i].ToString());
            }
            ViewBag.score = stuli;
            if (stuli.Length == 0) {
                ViewBag.sum_score = 0;
            } else {
                ViewBag.sum_score = Math.Round(Convert.ToDecimal(sum / (stuli.Length * 10) * 100), 2, MidpointRounding.AwayFromZero);
            }
            ViewBag.cost = costs;

            var pqlist = new List<SelectListItem>();
            var pid = (from i in db.exam_paper where i.exam.Id == exam.Id select i.paper.id).ToArray();
            var pq_id = new List<long>();
            for (int j = 0; j < pid.Count(); j++) {
                var paperid = pid[j];
                var pqid = (from i in db.paper_ques where i.paper.id == paperid select i.id).ToArray();
                for (int k = 0; k < pqid.Count(); k++) {
                    pq_id.Insert(pq_id.Count, pqid[k]);
                }                                     
            }

            for (var i = 0; i < pq_id.Count(); i++) {
                
                var item = new SelectListItem { Text = $"{i + 1}", Value = $"{pq_id[i]}" };
                pqlist.Add(item);
            }
            ViewBag.categories = pq_id.Count();
            ViewBag.QuesOptions = new SelectList(pqlist, "Value", "Text");
            var pqrows = (from i in db.stu_exam_right_answer
                          where i.ec_id == id && i.stu_id == stuid
                          select i).ToArray();
            return View(pqrows);
        }
        public ActionResult Exam_explanation(long id) {
            ViewBag.ecid = id;
            var examclass = db.exam_class.Find(id);
            var exam = examclass.exam;
            ViewBag.time = exam.start_time.ToString("yyyy/MM/dd")+" - "+ exam.end_time.ToString("yyyy/MM/dd");
            var duration = exam.test_time.ToString();
            string[] time = duration.Split(':');
            string[] chinese = { "小时", "分钟", "秒" };
            for (int i = 0; i < time.Length; i++)
                if (time[i]=="00") 
                    time[i] = "";
                else
                    time[i] = int.Parse(time[i]).ToString()+ chinese[i];
            ViewBag.duration = string.Join("",time);
            ViewBag.score = 100;
            ViewBag.attention = exam.attention;
            ViewBag.details = exam.details;
            return View();
        }
    }
}