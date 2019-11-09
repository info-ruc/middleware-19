using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Threading.Tasks;
using System.Data;
using System.Data.Entity;
using System.Net.Sockets;
using System.Net;
using moocweb.Models;
using jmail;


namespace moocweb.Controllers
{
    public class LoginController : Controller
    {
        // GET: Login
        private Entities db = new Entities();
        public ActionResult Login() {
            ViewBag.LoginState = "未登录";
            var li = db.college_info.ToList();
            var slist = new SelectList(li, "college_name", "college_name");
            foreach(SelectListItem item in slist) {
                if (item.Text == "中国人民大学") {
                    item.Selected = true;
                    //break;
                } 
            }
            ViewBag.Coptions = new SelectList(li, "college_name","college_name");
            return View();
        }
        [HttpPost]
        public ActionResult Login(TmpLogin impt) {
            var user = impt.uaccount; 
            var userinfo = db.user_account.FirstOrDefault(u => u.account == user.account && u.pass == user.pass && u.cname == user.cname);
            if(userinfo == null) {
                Response.Write("<script>alert('账号或密码输入错误');</script>");
            } else {
                Session.Timeout = 180;
                var level = userinfo.level;
                Session["account"] = userinfo.account;
                Session["college"] = userinfo.cname;
                Session["id"] = userinfo.id;
                Session["level"] = userinfo.level;
                string ip = Request.ServerVariables["REMODE_ADDR"];
                if(level == 0) {
                    var student = db.stu_info.Find(userinfo.id);
                    student.lastIP = ip;
                    db.Entry(student).State = EntityState.Modified;
                    db.SaveChangesAsync();
                } else {
                    var teacher = db.teacher_info.Find(userinfo.id);
                    teacher.lastIP = ip;
                    db.Entry(teacher).State = EntityState.Modified;
                    db.SaveChangesAsync();
                }
                switch (level) {
                    case 0:
                        return RedirectToAction("Account","Stuhome");
                    case 1:return RedirectToAction("Account", "Teacher");
                    case 2:return RedirectToAction("Account","Teacher");
                    case 3:return RedirectToAction("Account", "Teacher");
                    default:ViewBag.LoginState = "其他"; break;
                }
            }
            var li = db.college_info.ToList();
            ViewBag.Coptions = new SelectList(li, "college_name", "college_name");
            return View();
        }
        [HttpPost]
        public ActionResult Apply(TmpLogin impt) {
            var uapply = impt.uapply;
            uapply.isdelete = false;
            db.userapply.Add(uapply);
            string res = "";
            try { db.SaveChanges(); }
            catch (Exception except) {
                res = except.InnerException.InnerException.Message;
            }
            return Content(res);
        }

}
}