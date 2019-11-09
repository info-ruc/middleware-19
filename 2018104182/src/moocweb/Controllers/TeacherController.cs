using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Data.Entity;
using System.Web;
using System.Web.Mvc;
using System.Data;
using System.Threading.Tasks;
using System.Net;
using System.IO;
using moocweb.Models;
using moocweb.Filter;
using moocweb.Business;
using Newtonsoft.Json;
using System.Data.Entity.Validation;
using jmail;
namespace moocweb.Controllers
{
    [TeacherAuth]
    public class TeacherController : Controller
    {
        private Entities db = new Entities();
        private Teafunction tf = new Teafunction();
        public ActionResult Index()
        {
            return RedirectToAction("Account");
        }
        public ActionResult Exit()
        {
            var id = (long)Session["id"];
            var teacher = db.teacher_info.Find(id);  
            teacher.lastTime = DateTime.Now;
            db.Entry(teacher).State = EntityState.Modified;
            db.SaveChanges();
            Session.Abandon();
            return RedirectToAction("Login", "Login");
        }
        public ActionResult Account() {
            long id = (long)Session["id"];
            int level = (int)Session["level"];
            var user = db.teacher_info.Find(id);
            ViewBag.lv = level;
            switch(level) {
                case 1:ViewBag.level = "教师"; break;
                case 2:ViewBag.level = "校级管理员"; break;
                case 3:ViewBag.level = "超级管理员"; break;
                default: break;
            }
            teainfo tinfo = db.teainfo.Find(id);
            var li = db.college_info.ToList();                                  
            ViewBag.Coptions = new SelectList(li, "id", "college_name");
            var Loptions1 = new List<SelectListItem>(){
                new SelectListItem(){ Value = "1", Text = "教师"},
                new SelectListItem(){ Value = "2", Text = "校级管理员"},
                new SelectListItem(){ Value = "3", Text = "超级管理员"}
            };
            var Loptions2 = new List<SelectListItem>(){
                new SelectListItem(){ Value = "1", Text = "教师"},
            };
            if(level == 3) {
                ViewBag.Coptions = new SelectList(li, "id", "college_name");
                ViewBag.Loptions = Loptions1;
            } else {
                li = (from i in db.college_info where i.id == user.college_id select i).ToList();
                ViewBag.Coptions = new SelectList(li, "id", "college_name");
                ViewBag.Loptions = Loptions2;
            }
            return View(db.teainfo.Find(id));
        }
        [HttpPost]
        public ActionResult Account_Update(teacher_info teacher) {
            var res = "";
            teacher_info tea = db.teacher_info.Find(teacher.id);
            tea.tea_user = teacher.tea_user;
            tea.tea_name = teacher.tea_name;
            tea.pass = teacher.pass;
            db.Entry(tea).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        [HttpPost]
        public async Task<ActionResult> Update(teainfo teainf) {
            bool flag = false;
            long id = (long)Session["id"];
            teacher_info tea_info = await db.teacher_info.FindAsync(id);
            if(tea_info.pass != teainf.pass) {
                tea_info.pass = teainf.pass;
                flag = true;
            }
            if(tea_info.tea_name != teainf.tea_name){
                tea_info.tea_name = teainf.tea_name;
                flag = true;
            }
            if(flag == false) {
                Response.Write("<script>alert('未修改!');<script>");
            } else {
                db.Entry(tea_info).State = System.Data.Entity.EntityState.Modified;
                await db.SaveChangesAsync();
                Response.Write("<script>alert('修改成功');<script>");
            }
            return RedirectToAction("Account");
        }
        [HttpPost]
        public ActionResult Add(teainfo teainf) {//add account
            teacher_info tea_info = new teacher_info
            {
                college_id = long.Parse(teainf.college_name),
                pass = "123456",
                tea_user = teainf.tea_user,
                tea_level = teainf.tea_level,
                tea_name = teainf.tea_name,
                isdelete = false
            };
            db.teacher_info.Add(tea_info);
            db.SaveChanges();
            Response.Write("<script>alert('123456');<script>");
            return RedirectToAction("Account");
        }
        [HttpPost]
        public ActionResult Resetpass(teacher_info teacher) {
            var res = "";
            teacher_info tea = db.teacher_info.Find(teacher.id);
            tea.pass = teacher.pass;
            db.Entry(tea).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        [HttpPost]
        public JsonResult Get_teainfo() {
            int level = (int)Session["level"];
            teainfo[] list;
            if(level == 3) {
                list = db.teainfo.ToArray();              
            }else{
                string cname = (string)Session["college"];
                list = (from c in db.teainfo
                        where c.college_name == cname && c.tea_level <= level
                        select c).ToArray();
            }
            var json = new {
                total = list.Count(),
                rows = list
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult Delete(string id) {
            if (id == null) {
                var json1 = new {
                    result = '1'
                };
                return Json(json1,JsonRequestBehavior.AllowGet);
            }
            var nid = long.Parse(id);
            teacher_info tea_info = db.teacher_info.Find(nid);
            if (tea_info == null) {
                var json2 = new {
                    result = '2'
                };
                return Json(json2, JsonRequestBehavior.AllowGet);
            }
            tea_info.isdelete = true;
            db.Entry(tea_info).State = System.Data.Entity.EntityState.Modified;
            db.SaveChanges();
            var json0 = new {
                result = '0'
            };
            return Json(json0, JsonRequestBehavior.AllowGet);
        }
        protected override void Dispose(bool disposing) {//默认析构
            if (disposing) {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
        public ActionResult Class() {
            long id = (long)Session["id"];
            int level = (int)Session["level"];
            string cname = (string)Session["college"];
            var teaname = db.teacher_info.Find(id).tea_name;
            List<teacher_info> li;
            if(level == 1) {
                ViewBag.Toptions = new List<SelectListItem>(){
                new SelectListItem(){ Value = id.ToString(), Text = teaname},
            };
            }else if(level == 2) {
                li = (from i in db.teacher_info where i.college_info.college_name == cname && i.isdelete == false select i).ToList();
                ViewBag.Toptions = new SelectList(li, "id", "tea_name");
            } else {
                li = (from i in db.teacher_info where i.isdelete == false select i).ToList();
                ViewBag.Toptions = new SelectList(li, "id", "tea_name");
            }
            return View();
        }
        [HttpPost]
        public JsonResult Get_classinfo(long? id) {
            if (id == null) {
                int level = (int)Session["level"];
                classinfo[] list;
                if (level == 3) {
                    list = db.classinfo.ToArray();
                    var json = new {
                        total = list.Count(),
                        rows = list
                    };
                    return Json(json, JsonRequestBehavior.AllowGet);
                } else if (level == 2) {
                    string collegename = (string)Session["college"];
                    list = (from c in db.classinfo
                            join g in db.teainfo on c.tea_id equals g.id
                            where g.college_name == collegename
                            select c).ToArray();
                    var json = new {
                        total = list.Count(),
                        rows = list
                    };
                    return Json(json, JsonRequestBehavior.AllowGet);
                } else if (level == 1) {
                    id = (long)Session["id"];
                    list = (from c in db.classinfo
                            where c.tea_id == id
                            select c).ToArray();
                    var json = new {
                        total = list.Count(),
                        rows = list
                    };
                    return Json(json, JsonRequestBehavior.AllowGet);
                } else {
                    var json = new {
                        total = 0,
                    };
                    return Json(json, JsonRequestBehavior.AllowGet);
                }
            } else {
                var list = (from c in db.classinfo
                            where c.tea_id == id
                            select c).ToArray();
                var json = new {
                    total = list.Count(),
                    rows = list
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            }
        }
        [HttpPost]
        public ActionResult Add_class(@class clas) {
            clas.isdelete = false;
            clas.create_time = DateTime.Now;
            db.@class.Add(clas);
            db.SaveChanges();
            var json = new {
                result = 0,
            };
            return RedirectToAction("Class");
        }
        [HttpPost]
        public ActionResult Update_class(@class clas) {
            var res = "";
            @class cl = db.@class.Find(clas.class_id);
            cl.class_name = clas.class_name;
            cl.describe = clas.describe;
            db.Entry(cl).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch(DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        [HttpPost]
        public JsonResult Del_Class(string id) {
            if (id == null) {
                var json1 = new {
                    result = '1'
                };
                return Json(json1, JsonRequestBehavior.AllowGet);
            }
            var nid = long.Parse(id);
            @class clas = db.@class.Find(nid);
            if (clas == null) {
                var json2 = new {
                    result = '2'
                };
                return Json(json2, JsonRequestBehavior.AllowGet);
            }
            clas.isdelete = true;
            db.Entry(clas).State = System.Data.Entity.EntityState.Modified;
            db.SaveChanges();
            var json0 = new {
                result = '0'
            };
            return Json(json0, JsonRequestBehavior.AllowGet);
        }
        public ActionResult StuClass(long? id) {
            ViewBag.id = id;
            ViewBag.classname = db.@class.Find(id).class_name;
            return View();
        }
        public JsonResult StuClass_info(long? id) {
            StuClass[] list = (from c in db.StuClass
                    where c.class_id == id
                    select c).ToArray();
            var json = new {
                total = list.Count(),
                rows = list
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public ActionResult Add_Stu(string stunum, string id) {
            long sid = long.Parse(id);
            long? cid = db.@class.Find(sid).teacher_info.college_id;
            var res = "";
            try {
                var c = db.MassInsertStu(stunum, sid, cid) / 2;
            }catch(Exception e) {
                res = e.Message;
            }
            return Content(res);
        }
        
        [HttpPost]
        public ActionResult Add_Stu_File(HttpPostedFileBase upload, long sid) {

            var filename = Path.GetFileName(upload.FileName);
            var path = Path.Combine(Server.MapPath("~/Temp"), filename);
            upload.SaveAs(path);

            var li = db.stu_info;
            long? cid = db.@class.Find(sid).teacher_info.college_id;

            var res = "";
            FileStream fs = new FileStream(path, FileMode.Open, FileAccess.Read, FileShare.None);
            StreamReader sr = new StreamReader(fs, System.Text.UTF8Encoding.UTF8);

            string str = "";
            string s = Console.ReadLine();
            int i = 0;
            while (str != null) {
                str = sr.ReadLine();
                if (str == null)
                    break;
                string[] stu = str.Split(',');
                string stu_id = stu[0];
                string stu_name = stu[1].Replace("\"", "");//过滤双引号
                if (i != 0)//过滤表头
                {
                    var query = (from q in li where (q.stu_num == stu_id) && (q.college_id == cid) select q).SingleOrDefault();
                    if (query != null) {
                        stu_class sc = new stu_class
                        {
                            stu_id = query.id,
                            class_id = cid
                        };
                        db.stu_class.Add(sc);
                    } else {
                        stu_info sstu = new stu_info
                        {
                            stu_num = stu_id,
                            pass = "123456",
                            college_id = cid,
                            name = stu_name
                        };
                        db.stu_info.Add(sstu);
                        //stu_class sc = new stu_class
                        //{
                            //stu_id = sstu.id,
                            //class_id = cid
                        //};
                        //db.stu_class.Add(sc);
                    }
                 }
                 i++;
            }
            try { db.SaveChanges(); }
            catch (DbEntityValidationException w) {
                res = tf.AnalyseError(w);
            }
            return Content(res);
        }


        [HttpPost]
        public ActionResult Update_stu(stu_class stucls) {
            var res = "";
            stu_info sc = db.stu_info.Find(db.stu_class.Find(stucls.id).stu_id);
            sc.stu_num = stucls.stu_info.stu_num;
            sc.name = stucls.stu_info.name;
            sc.gender = stucls.stu_info.gender;
            sc.pass = stucls.stu_info.pass;
            db.Entry(sc).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        [HttpPost]
        public ActionResult Del_StuCls(long id){
            var stuCls = db.stu_class.Find(id);
            db.stu_class.Remove(stuCls);
            var res = "";
            try { db.SaveChanges(); }
            catch(DbEntityValidationException e){
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        public ActionResult Qbase() {
            var li = db.teacher_info;
            int level = (int)Session["level"];
            long id = (long)Session["id"];
            string cname = (string)Session["college"];
            if(level == 3) {
                ViewBag.Toptions = new SelectList(li.ToList(), "id", "tea_name");
            }else if(level == 2) {
                var tli = (from i in li where i.college_info.college_name == cname select i).ToList();
                ViewBag.Toptions = new SelectList(tli, "id", "tea_name");
            }
            else {
                var tli = (from i in li where i.id == id select i).ToList();
                ViewBag.Toptions = new SelectList(tli, "id", "tea_name");
            }
            return View();
        }
        public ActionResult QbaseEdit(long? id) {
            ViewBag.id = id;
            ViewBag.name = db.qbase_set.Find(id).set_name;
            var li = db.background.ToList();
            ViewBag.Boptions = new SelectList(li, "id", "describe");
            return View();
        }
        [HttpPost]
        public JsonResult Qbaseset() {
            int level = (int)Session["level"];
            if (level == 3) {
                qbaseset[] list = db.qbaseset.ToArray();
                var json = new {
                    total = list.Count(),
                    rows = list
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            } else if (level == 2) {
                string cname = (string)Session["college"];
                qbaseset[] list = (from i in db.qbaseset
                                   where i.isopen == true || (i.isopen == false && (from j in db.teainfo where j.college_name == cname select j.id).Contains((long)i.admin_id))
                                   select i).ToArray();
                var json = new {
                    total = list.Count(),
                    rows = list
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            } else {
                var id = (long)Session["id"];
                qbaseset[] list = (from i in db.qbaseset
                                   where i.isopen == true || (i.isopen == false && i.admin_id == id)
                                   select i).ToArray();
                var json = new {
                    total = list.Count(),
                    rows = list
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            }
        }
        [HttpPost]
        public ActionResult Qbaseset_Update(@qbase_set qbset) {
            var res = "";
            @qbase_set qbs = db.@qbase_set.Find(qbset.id);
            qbs.set_name = qbset.set_name;
            qbs.describe = qbset.describe;
            db.Entry(qbs).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        [HttpPost]
        public JsonResult Qbaseset_Del(string id) {
            var sid = long.Parse(id);
            var qbset = db.qbase_set.Find(sid);
            if(qbset == null) {
                var json = new {
                    res = 1,
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            }
            qbset.isdelete = true;
            db.Entry(qbset).State = System.Data.Entity.EntityState.Modified;
            db.SaveChanges();
            var json0 = new {
                result = 0,
            };
            return Json(json0, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult Qbasechapter(long? id) {
            qbasechapter[] list = (from c in db.qbasechapter
                                   where c.setid == id
                                   select c).ToArray();
            var json = new {
                total = list.Count(),
                rows = list
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult QchapSelectItem(long id) {
            if(id == 0) {
                var data = (from c in db.qbasechapter select new { c.id, text = c.chap_name }).ToList();
                data.Add(new {  id = (long)0, text = " " });
                return Json(data, JsonRequestBehavior.AllowGet);
            } else {
                var data = (from c in db.qbasechapter where c.setid == id select new { c.id, text = c.chap_name }).ToList();
                data.Add(new { id = (long)0, text = " " });
                return Json(data, JsonRequestBehavior.AllowGet);
            }
        }
        [HttpPost]
        public ActionResult Qbasechapter_Update(@qbase_chapter qbchap) {
            var res = "";
            @qbase_chapter qbc = db.@qbase_chapter.Find(qbchap.id);
            qbc.chap_name = qbchap.chap_name;
            qbc.describe = qbchap.describe;
            //qbchap.background = db.background.Find(qbchap.background.id);
            db.Entry(qbc).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        [HttpPost]
        public JsonResult Qbasechapter_Del(string id) {
            var cid = long.Parse(id);
            var qbchap = db.qbase_chapter.Find(cid);
            if(qbchap == null) {
                var json = new {
                    res = 1,
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            }
            qbchap.isdelete = true;
            db.Entry(qbchap).State = System.Data.Entity.EntityState.Modified;
            db.SaveChanges();
            var json0 = new {
                result = 0,
            };
            return Json(json0, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public ActionResult Add_QbaseSet(qbase_set inpt) {
            inpt.isdelete = false;
            inpt.isopen = true;
            db.qbase_set.Add(inpt);
            db.SaveChanges();
            return RedirectToAction("Qbase");
        }
        [HttpPost]
        public ActionResult Add_QbaseChapter(qbase_chapter inpt) {
            inpt.isdelete = false;
            inpt.background = db.background.Find(inpt.background.id);
            db.qbase_chapter.Add(inpt);
            try {
                db.SaveChanges();
            }
            catch (DbEntityValidationException ex) {
    
                System.Text.StringBuilder errors = new System.Text.StringBuilder();
                IEnumerable<DbEntityValidationResult> validationResult = ex.EntityValidationErrors;
                foreach (DbEntityValidationResult result in validationResult) {
                    ICollection<DbValidationError> validationError = result.ValidationErrors;
                    foreach (DbValidationError err in validationError) {
                        errors.Append(err.PropertyName + ":" + err.ErrorMessage + "\r\n");
                    }
                }
            }
            return RedirectToAction("QbaseEdit",new { id = inpt.setid});
        }
        public ActionResult Background() {
            var DBselections = new SelectListItem[3];
            DBselections[0] = new SelectListItem {Text = "Kingbase",Value = "Kingbase" };
            DBselections[1] = new SelectListItem { Text = "Mysql", Value = "Mysql" };
            DBselections[2] = new SelectListItem { Text = "SQL server", Value = "MSSQL" };
            ViewBag.DBoptions = new SelectList(DBselections.ToList(), "Value", "Text");
            return View();
        }
        [HttpPost]
        public JsonResult Backinfo() {
            var data = from m in db.background
                       select new {
                           m.id,
                           m.name,
                           m.sql,
                           m.describe,
                           m.db
                       };
            var list = data.ToArray();
            var json = new {
                total = list.Count(),
                rows = list
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        public ActionResult AddBack(background back) {
            var result = "";
            db.background.Add(back);
            if(back.db == "Kingbase") {
                var kingbaseconn = new KingbaseConn();
                result = kingbaseconn.CreateBackGround(back);
                kingbaseconn.Dispose();
            }else if(back.db == "Mysql") {
                var mysqlconn = new MysqlConn();
                result = mysqlconn.AddBack(back.name, back.sql);
                mysqlconn.Dispose();
            }else if (back.db == "MSSQL") {
                var mssqlconn = new MSSQLConn();
                result = mssqlconn.AddBack(back.name, back.sql);
                mssqlconn.Dispose(); 
            }
            else{
                result = "暂不支持该数据库";
            }
            if(result != "") {
                return Content(result);
            }
            try { db.SaveChanges(); }
            catch (Exception ex) {
                result = ex.InnerException.InnerException.Message;
            }
            return Content(result);
        }
        [HttpPost]
        public ActionResult Update_back(@background back) {
            var res = "";
            @background bk = db.@background.Find(back.id);
            bk.name = back.name;
            bk.describe = back.describe;
            //bk.db = back.db;
            //bk.sql = back.sql;
            db.Entry(bk).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        public ActionResult BackEdit(long? id) {
            ViewBag.id = id;
            ViewBag.name = db.background.Find(id).name;
            return View();
        }
        public JsonResult BackDetail(long id) {
            background back = db.background.Find(id);
            DataSet[] res = null;
            if(back.db == "Kingbase") {
                var kingbaseconn = new KingbaseConn();
                res = kingbaseconn.ShowBackGround(back.name);
                kingbaseconn.Dispose();
            }else if(back.db == "Mysql") {
                var mysqlconn = new MysqlConn();
                res = mysqlconn.ShowBackGround(back.name);
                mysqlconn.Dispose();
            }else if(back.db == "MSSQL") {
                var mssqlconn = new MSSQLConn();
                res = mssqlconn.ShowBackGround(back.name);
                mssqlconn.Dispose();
            }
            else {
                res = new DataSet[0];
            }
            int count = res.Count();
            var tname = new List<string>();
            var teafunc = new Teafunction();
            string[][] colnames = new string[count][];
            string[] bnames = new string[count];
            string[] jdatas = new string[count];
            for (int i = 0; i < count; i++) {
                var ds = res[i];
                tname.Add(ds.DataSetName);
                colnames[i] = new string[ds.Tables[0].Columns.Count];
                int j = 0;
                foreach(DataColumn col in ds.Tables[0].Columns) {
                    colnames[i][j] = new string(col.ColumnName.ToCharArray());
                    j++;
                }
                string tmp = teafunc.DataTableToJsonWithJsonNet(ds.Tables[0]);
                Dictionary<string, object> jdata = new Dictionary<string, object>();
                jdata.Add("total", ds.Tables[0].Rows.Count);
                jdata.Add("rows", ds.Tables[0]);
                bnames[i] = $"/Backrows/back{id}table{i}.json";
                System.IO.File.WriteAllText(Server.MapPath(bnames[i]), JsonConvert.SerializeObject(jdata));
            }
            var json = new {
                count,
                tname,
                colnames,
                bnames
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public ActionResult UploadFile(HttpPostedFileBase file,long bid,int tnum, string character) {
            //处理file，识别格式，Ctrl
            var path = $"/UploadFile/{file.FileName}";
            string pyspath = Server.MapPath(path);
            file.SaveAs(pyspath);
            char deli = ',';
            var back = db.background.Find(bid);
            var back_name = back.name;
            if(back.db == "Kingbase") {
                var kingbaseconn = new KingbaseConn();
                var p = kingbaseconn.InsertData(pyspath, deli, back_name, tnum, file.FileName);
                kingbaseconn.Dispose();
                return Content(p.Item1);
            }else if(back.db == "Mysql") {
                var mysqlconn = new MysqlConn();
                var tname = mysqlconn.GetSchemaInfo(back_name)[tnum];
                var p = mysqlconn.Insertdata(pyspath, deli, back_name, tname, character);
                mysqlconn.Dispose();
                return Content(p);
            }else if(back.db == "MSSQL") {
                var mssqlconn = new MSSQLConn();
                var tname = mssqlconn.GetSchemaInfo(back_name)[tnum];
                var p = mssqlconn.Insertdata(pyspath, deli, back_name, tname, character);
                mssqlconn.Dispose();
                return Content(p);
            }
            else {
                return Content("");
            }
        }

        [HttpPost]
        public ActionResult ClearBackData(long bid,int tnum) {
            var back_name = db.background.Find(bid).name;
            var dbtype = db.background.Find(bid).db;
            var p = "";
            if(dbtype == "Kingbase") {
                var kingbaseconn = new KingbaseConn();
                p = kingbaseconn.ClearTable(back_name, tnum);
                kingbaseconn.Dispose();
            }else if(dbtype == "Mysql") {
                var mysqlconn = new MysqlConn();
                var tname = mysqlconn.GetSchemaInfo(back_name)[tnum];
                p = mysqlconn.Cleartable(back_name, tname);
                mysqlconn.Dispose();
            }else if(dbtype == "MSSQL") {
                var mssqlconn = new MSSQLConn();
                var tname = mssqlconn.GetSchemaInfo(back_name)[tnum];
                p = mssqlconn.Cleartable(back_name, tname);
                mssqlconn.Dispose();
            }
            return Content(p);
        }
        public ActionResult Questions(long id) {
            ViewBag.id = id;
            ViewBag.name = db.qbase_chapter.Find(id).chap_name;
            return PartialView();
        }
        [HttpPost]
        public JsonResult Question_info(string id, int qtype) {
            var cid = long.Parse(id);
            ques[] ques;
            if (qtype == 2) {
                ques = (from q in db.ques
                        where q.chap_id == cid && q.q_type <= 2
                        select q).ToArray();
            } else {
                ques = (from q in db.ques
                        where q.chap_id == cid && q.q_type == qtype
                        select q).ToArray();
            }

            var json = new {
                total = ques.Count(),
                rows = ques
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult Add_SQL_Question(string chap_id,string title, string ans,string[] judges,string[] values) {
            long cid = long.Parse(chap_id);
            var id = (long)Session["id"];
            var back = db.qbase_chapter.Find(cid).background;
            string back_name = back.name;
            ans = ans.ToUpper();
            for(var i = 0; i < judges.Length; i++) {
                judges[i] = judges[i].ToUpper();
            }
            question ques = new question
            {
                chap_id = cid,
                title = title,
                right_answer = ans,
                q_type = 1
            };
            string uname = db.teacher_info.Find(id).tea_name;
            if(back.db == "Kingbase") {
                var kingbaseconn = new KingbaseConn();
                var runres = kingbaseconn.RunSql(ans, back_name, true);
                if (runres.Item1 == "null") {
                    kingbaseconn.Dispose();
                    return Json(new { res = 1, excep = "执行无结果返回，请检查背景数据信息或结果语句" }, JsonRequestBehavior.AllowGet);
                } else if (runres.Item2 == false) {
                    kingbaseconn.Dispose();
                    return Json(new { res = 1, excep = $"语句执行出错，错误信息为:\n{runres.Item1}" }, JsonRequestBehavior.AllowGet);
                }
                kingbaseconn.Dispose();
                ques.output = runres.Item1;
            }
            else if (back.db == "Mysql") {
                var mysqlconn = new MysqlConn();
                var runres = mysqlconn.RunSql(uname, ans, back_name, false, true);
                if (!runres.Item3) {
                    mysqlconn.Dispose();
                    return Json(new { res = 1, excep = $"语句执行出错，错误信息为:\n{runres.Item2}" }, JsonRequestBehavior.AllowGet);
                }else if(runres.Item1.Rows.Count == 0) {
                    mysqlconn.Dispose();
                    return Json(new { res = 1, excep = "执行无结果返回，请检查背景数据信息或结果语句" }, JsonRequestBehavior.AllowGet);
                }
                mysqlconn.Dispose();
                foreach(var i in runres.Item1.Columns) {
                    ques.output += $"{i.ToString()} ";
                }
            } else if (back.db == "MSSQL") {
                var mssqlconn = new MSSQLConn();
                var runres = mssqlconn.RunSql(uname, ans, back_name, false, true);
                if (!runres.Item3) {
                    mssqlconn.Dispose();
                    return Json(new { res = 1, excep = $"语句执行出错，错误信息为:\n{runres.Item2}" }, JsonRequestBehavior.AllowGet);
                } else if (runres.Item1.Rows.Count == 0) {
                    mssqlconn.Dispose();
                    return Json(new { res = 1, excep = "执行无结果返回，请检查背景数据信息或结果语句" }, JsonRequestBehavior.AllowGet);
                }
                mssqlconn.Dispose();
                foreach (var i in runres.Item1.Columns) {
                    ques.output += $"{i.ToString()} ";
                }
            }
            int res = 1;
            string excep = null;
            db.question.Add(ques);
            try {
                db.SaveChanges();
                res = 0;
            }
            catch (DbEntityValidationException ex) {
                excep = tf.AnalyseError(ex);
                return Json(new { res, excep }, JsonRequestBehavior.AllowGet);
            }
            res = 1;
            var lid = db.question.OrderByDescending(e => e.id).FirstOrDefault().id;
            var Dic = Server.MapPath("/flex");
            tf.GenrateL(judges, values, Dic);
            var path = Server.MapPath("/flex/flex.exe");
            var inpt = Server.MapPath("/flex/tmp.l");
            var outpt = tf.RunExe(path, inpt, Dic);
            if (outpt != "") {
                excep = $"正则表达式输入有误";
                return Json(new { res, excep }, JsonRequestBehavior.AllowGet);
            }
            tf.CompileC($"question{lid}", Dic);
            res = 0;
            var json = new {
                res,
                excep
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult Add_DQL_Question(string chap_id,string title,string ans,string[] judges,string[] values,string check) {
            long cid = long.Parse(chap_id);
            var id = (long)Session["id"];
            string uname = db.teacher_info.Find(id).tea_name;
            ans = ans.ToUpper();
            for (var i = 0; i < judges.Length; i++) {
                judges[i] = judges[i].ToUpper();
            }
            var back = db.qbase_chapter.Find(cid).background;
            string back_name = back.name;
            question ques = new question
            {
                chap_id = cid,
                title = title,
                right_answer = ans,
                q_type = 2,
                check_sentence = check
            };
            if(back.db == "Kingbase") {
                var kingbaseconn = new KingbaseConn();
                var runres = kingbaseconn.RunNonQueSql(ans, back_name, false);
                back_name += "tmp";
                if (runres.Item2 == false) {
                    kingbaseconn.DeleteBack(back_name);
                    kingbaseconn.Dispose();
                    return Json(new { res = 1, excep = $"答案语句执行出错，错误信息为:\n{runres.Item1}" }, JsonRequestBehavior.AllowGet);
                }
                var runres2 = kingbaseconn.RunSql(check, back_name, true);
                if (runres2.Item2 == false) {
                    kingbaseconn.Dispose();
                    kingbaseconn.DeleteBack(back_name);
                    return Json(new { res = 1, excep = $"检查语句执行出错，错误信息为:\n{runres.Item1}" }, JsonRequestBehavior.AllowGet);
                }
                kingbaseconn.DeleteBack(back_name);
                kingbaseconn.Dispose();
                ques.output = runres2.Item1;
            }else if(back.db == "Mysql") {
                var mysqlconn = new MysqlConn();
                var runres = mysqlconn.RunSql(uname, ans, back_name, true, false);
                if(runres.Item3 == false) {
                    mysqlconn.Dispose();
                    return Json(new { res = 1, excep = $"答案语句执行出错，错误信息为:\n{runres.Item2}" }, JsonRequestBehavior.AllowGet);
                }
                var runres2 = mysqlconn.RunSql(uname, check, back_name, false, true);
                if(runres2.Item3 == false) {
                    mysqlconn.Dispose();
                    return Json(new { res = 1, excep = $"检查语句执行出错，错误信息为:\n{runres.Item1}" }, JsonRequestBehavior.AllowGet);
                }
                mysqlconn.Dispose();
                foreach (var i in runres.Item1.Columns) {
                    ques.output += $"{i.ToString()} ";
                }
            } else if (back.db == "MSSQL") {
                var mssqlconn = new MSSQLConn();
                var runres = mssqlconn.RunSql(uname, ans, back_name, true, false);
                if (runres.Item3 == false) {
                    mssqlconn.Dispose();
                    return Json(new { res = 1, excep = $"答案语句执行出错，错误信息为:\n{runres.Item2}" }, JsonRequestBehavior.AllowGet);
                }
                var runres2 = mssqlconn.RunSql(uname, check, back_name, false, true);
                if (runres2.Item3 == false) {
                    mssqlconn.Dispose();
                    return Json(new { res = 1, excep = $"检查语句执行出错，错误信息为:\n{runres.Item1}" }, JsonRequestBehavior.AllowGet);
                }
                mssqlconn.Dispose();
                foreach (var i in runres.Item1.Columns) {
                    ques.output += $"{i.ToString()} ";
                }
            }

            db.question.Add(ques);
            int res = 1;
            string excep = null;
            try {
                db.SaveChanges();
                res = 0;
            }
            catch (Exception ex) {
                excep = ex.InnerException.InnerException.Message;
                return Json(new { res, excep }, JsonRequestBehavior.AllowGet);
            }
            var lid = db.question.OrderByDescending(e => e.id).FirstOrDefault().id;
            var Dic = Server.MapPath("/flex");
            tf.GenrateL(judges, values, Dic);
            var path = Server.MapPath("/flex/flex.exe");
            var inpt = Server.MapPath("/flex/tmp.l");
            var outpt = tf.RunExe(path, inpt, Dic);
            if (outpt != "") {
                excep = $"正则表达式输入有误";
                return Json(new { res, excep }, JsonRequestBehavior.AllowGet);
            }
            tf.CompileC($"question{lid}", Dic);
            var json = new {
                res,
                excep
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult Add_SC_Question(string chap_id, string title, string ans1, string ans2, string ans3, string ans4, string ans5, string ans6, string right_answer, short q_type) {
            long cid = long.Parse(chap_id);
            var id = (long)Session["id"];
            int res = 1;
            string excep = null;
            int a = -1;
            if (right_answer == "") {
                return Json(new { a, excep }, JsonRequestBehavior.AllowGet);
            }
            question ques = new question
            {
                chap_id = cid,
                title = title,
                right_answer = right_answer,
                q_type = q_type,
                ans1 = ans1,
                ans2 = ans2,
                ans3 = ans3,
                ans4 = ans4,
                ans5 = ans5,
                ans6 = ans6
            };
            db.question.Add(ques);
            try {
                db.SaveChanges();
                res = 0;
            }
            catch (DbEntityValidationException ex) {
                excep = tf.AnalyseError(ex);
                return Json(new { res, excep }, JsonRequestBehavior.AllowGet);
            }
            var json = new {
                res,
                excep
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public ActionResult Del_Question(long id){
            var quespaper = from i in db.paper_ques where i.question.id == id select i;
            var stulogs = from j in db.stu_ans_log
                          where (from i in quespaper select i.id).Contains(j.paper_ques.id)
                          select j;
            var stu_quesans = from j in db.stu_paperques
                              where (from i in quespaper select i.id).Contains(j.paper_ques.id)
                              select j;
            db.stu_paperques.RemoveRange(stu_quesans);
            db.stu_ans_log.RemoveRange(stulogs);
            db.paper_ques.RemoveRange(quespaper);
            var question = db.question.Find(id);
            question.isdelete = true;
            db.Entry(question).State = EntityState.Modified;
            var res = "";
            try { db.SaveChanges(); }
            catch(Exception e){
                res = e.InnerException.InnerException.Message;
            }
            return Content(res);

        }
        public ActionResult PaperManage() {
            return View();
        }
        public ActionResult Paper() {
            int level = (int)Session["level"];
            switch (level) {
                case 1:
                    return RedirectToAction("Teapaper",new {id = (long)Session["id"] });
                default:
                    return RedirectToAction("PaperManage");
            }
        }
        [HttpPost]
        public JsonResult Tea_paper_info() {
            var level = (int)Session["level"];
            if(level == 3) {
                var data = db.teapaper.ToArray();
                var json = new {
                    total = data.Count(),
                    rows = data
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            } else {
                string cname = (string)Session["college"];
                var data = (from i in db.teapaper
                            where (from j in db.teainfo where j.college_name == cname select j.id).Contains(i.admin_tea)
                            select i).ToArray();
                var json = new {
                    total = data.Count(),
                    rows = data
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            }
        }
        public ActionResult Teapaper(long id) {
            ViewBag.id = id;
            ViewBag.name = db.teacher_info.Find(id).tea_name;
            return View();
        }
        [HttpPost]
        public JsonResult Tea_paper_detail(long id) {
            var data = from p in db.paperquestion where p.admin_tea == id select p;
            var json = new {
                total = data.Count(),
                rows = data
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public ActionResult Add_Paper(paper inpt) {
            inpt.create_time = DateTime.Now;
            inpt.teacher_info = db.teacher_info.Find(inpt.teacher_info.id);
            db.paper.Add(inpt);
            string excep = "";
            try { db.SaveChanges(); }
            catch (Exception e) {
                excep = e.InnerException.InnerException.Message;
            }
            return Content(excep);
        }
        [HttpPost]
        public ActionResult Del_Paper(long id) {
            var paper = db.paper.Find(id);
            paper.isdelete = true;
            var res = "";
            try {
                db.Entry(paper).State = System.Data.Entity.EntityState.Modified;
                db.SaveChanges();
            }catch (Exception e) {
                res = e.InnerException.InnerException.Message;
            }
            return Content(res);
        }
        [HttpPost]
        public ActionResult Update_Paper(paper inpt) {
            var paper = db.paper.Find(inpt.id);
            var res = "";
            paper.isopen = inpt.isopen;
            paper.paper_name = inpt.paper_name;
            db.Entry(paper).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch(DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        public ActionResult PaperDetail(long id) {
            ViewBag.id = id;
            var li = db.qbaseset.ToList();
            var qbaselist = new SelectList(li, "id", "set_name").ToList();
            qbaselist.Insert(0, (new SelectListItem { Text = "全部", Value = "0" }));
            var qblist = new SelectList(qbaselist,"Value","Text");
            ViewBag.qbaselist = qblist;
            ViewBag.name = db.paper.Find(id).paper_name;
            var mlist = (from i in db.quesanswer
                         where i.paper_id == id
                         select i).ToList();
            return View(mlist);
        }
        //展示题库中题目，将已在id试卷中的题标记
        [HttpPost]
        public JsonResult Quesans_info(long id) {
            var ques = from q in db.ques
                       where
                       (from qa in db.quesanswer where qa.paper_id == id select qa.id).Contains(q.id)
                       select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = true };
            var ques1 = from q in db.ques
                        where !(from qa in db.quesanswer where qa.paper_id == id select qa.id).Contains(q.id)
                        select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = false };
            var data = ques.Union(ques1).ToList();
            var json = new {
                total = data.Count(),
                rows = data
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        //带题库集，题库章条件的展示题库信息
        [HttpPost]
        public JsonResult Quesans_info_qbase(long pid, long qbid, long qcid, short? qtype) {
            if (qtype == 0) {
                if (qcid == 0) {
                    var ques = from q in db.ques
                               join p in db.qbasechapter on q.chap_id equals p.id
                               where
                               (from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && p.setid == qbid
                               select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = true };
                    var ques1 = from q in db.ques
                                join p in db.qbasechapter on q.chap_id equals p.id
                                where !(from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && p.setid == qbid
                                select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = false };
                    var data = ques.Union(ques1).ToList();
                    var json = new {
                        total = data.Count(),
                        rows = data
                    };
                    return Json(json, JsonRequestBehavior.AllowGet);
                } else {
                    var ques = from q in db.ques
                               where
                               (from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.chap_id == qcid
                               select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = true };
                    var ques1 = from q in db.ques
                                where !(from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.chap_id == qcid
                                select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = false };
                    var data = ques.Union(ques1).ToList();
                    var json = new {
                        total = data.Count(),
                        rows = data
                    };
                    return Json(json, JsonRequestBehavior.AllowGet);
                }
            } else {
                if (qtype == 2) {
                    if (qcid == 0) {
                        var ques = from q in db.ques
                                   where
                                   (from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.q_type <= qtype
                                   select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = true };
                        var ques1 = from q in db.ques
                                    where !(from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.q_type <= qtype
                                    select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = false };
                        var data = ques.Union(ques1).ToList();
                        var json = new {
                            total = data.Count(),
                            rows = data
                        };
                        return Json(json, JsonRequestBehavior.AllowGet);
                    } else {
                        var ques = from q in db.ques
                                   where
                                   (from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.chap_id == qcid && q.q_type <= qtype
                                   select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = true };
                        var ques1 = from q in db.ques
                                    where !(from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.chap_id == qcid && q.q_type <= qtype
                                    select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = false };
                        var data = ques.Union(ques1).ToList();
                        var json = new {
                            total = data.Count(),
                            rows = data
                        };
                        return Json(json, JsonRequestBehavior.AllowGet);
                    }

                } else {
                    if (qcid == 0) {
                        var ques = from q in db.ques
                                   where
                                   (from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.q_type == qtype
                                   select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = true };
                        var ques1 = from q in db.ques
                                    where !(from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.q_type == qtype
                                    select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = false };
                        var data = ques.Union(ques1).ToList();
                        var json = new {
                            total = data.Count(),
                            rows = data
                        };
                        return Json(json, JsonRequestBehavior.AllowGet);
                    } else {
                        var ques = from q in db.ques
                                   where
                                   (from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.chap_id == qcid && q.q_type == qtype
                                   select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = true };
                        var ques1 = from q in db.ques
                                    where !(from qa in db.quesanswer where qa.paper_id == pid select qa.id).Contains(q.id) && q.chap_id == qcid && q.q_type == qtype
                                    select new { q.id, q.hard_level, q.q_type, q.title, q.chap_name, q.back_describe, iselect = false };
                        var data = ques.Union(ques1).ToList();
                        var json = new {
                            total = data.Count(),
                            rows = data
                        };
                        return Json(json, JsonRequestBehavior.AllowGet);
                    }
                }
            }
        }
        //Make_Paper存在问题，无法批量导入，模型中未包含外键列，与数据库中表不对应
        [HttpPost]
        public ActionResult Make_Paper(long[] idlist, long pid) {
            var paper = db.paper.Find(pid);
            List<paper_ques> li = new List<paper_ques>();
            var type = paper.type;
            bool can_add_objective;
            if (type == 2)
                can_add_objective = true;
            else
                can_add_objective = false;
            int result;
            foreach (long qid in idlist) {
                if (qid == 0) {
                    continue;
                }
                var ques = db.question.Find(qid);
                if (!can_add_objective) {
                    if (ques.q_type > 2) {
                        result = -2;
                        return Content(result.ToString());
                    }
                }
                var paperques = new paper_ques
                {
                    paper = paper,
                    question = ques,
                    nowvalue = ques.totvalue
                };
                li.Add(paperques);
            }
            if (li.Count() > 1) {
                /*
                db.BulkInsert(li);
                int result = li.Count();
                try { db.BulkSaveChanges(); }
                catch (Exception e) {
                    result = -1;
                }
                return Content(result.ToString());
                */
                db.paper_ques.AddRange(li);
            } else {
                db.paper_ques.Add(li[0]);
            }
            result = li.Count();
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                result = -1;
            }
            return Content(result.ToString());
        }
        [HttpPost]
        public ActionResult Del_Paperques(long id) {
            var paperques = db.paper_ques.Find(id);
            var stu_log = from i in db.stu_ans_log where i.paper_ques.id == id select i;
            var stu_ques = from i in db.stu_paperques where i.paper_ques.id == id select i;
            db.stu_ans_log.RemoveRange(stu_log);
            db.stu_paperques.RemoveRange(stu_ques);
            db.paper_ques.Remove(paperques);
            var res = "";
            try { db.SaveChanges(); }
            catch(DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);        
        }
        public ActionResult Exam(long? id) {
            int level = (int)Session["level"];
            if (id == null) {
                if (level >= 2) {
                    return RedirectToAction("AdminExam");
                }
                id = (long)Session["id"];
            } else {
                ViewBag.id = id;
            }
            ViewBag.id = id;
            ViewBag.name = db.teacher_info.Find(id).tea_name;
            ViewBag.classes = (from c in db.classinfo where c.tea_id == id select c).ToList();
            return View();
        }
        public ActionResult AdminExam() {
            int level = (int)Session["level"];
            if (level < 2) {
                return RedirectToAction("Exam");
            } else {
                ViewBag.level = level;
                long id = (long)Session["id"];
                string cname = (string)Session["college"];
                ViewBag.id = id;
                ViewBag.name = db.teacher_info.Find(id).tea_name;
                if(level == 2) {
                    var li = (from i in db.teainfo where i.college_name == cname select i).ToList();
                    ViewBag.Toptions = new SelectList(li, "id", "tea_name");
                } else {
                    var li = db.teainfo.ToList();
                    ViewBag.Toptions = new SelectList(li, "id", "tea_name");
                }
                return View();
            }
        }
        public ActionResult ExamPaper(long id) {
            ViewBag.id = id;
            var exam = db.exam.Find(id);
            ViewBag.name = exam.name;
            return View();
        }
        [HttpPost]
        public JsonResult ExamClass_info(long? id) {
            if(id == null) {
                id = (long)Session["id"];
            }
            var data = (from i in db.examclass where i.tea_id == id select i).ToList();
            var json = new {
                total = data.Count(),
                rows = data
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult ExamAdmin_info() {
            int level = (int)Session["level"];
            string cname = (string)Session["college"];
            long cid = db.college_info.FirstOrDefault(u => u.college_name == cname).id;
            if(level == 2) {
                var data = (from i in db.examclass where i.college_id == cid select i).ToList();
                var json = new {
                    total = data.Count(),
                    rows = data
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            } else {
                var data = db.examclass.ToList();
                var json = new {
                    total = data.Count(),
                    rows = data
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            }
        }
        [HttpPost]
        public JsonResult ExamPaper_info(long id) {
            var data = (from i in db.exampaper where i.exam_id == id select i).ToArray();
            var json = new {
                total = data.Count(),
                rows = data
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        public JsonResult PaperSelect_info(long id) {
            long tid = (long)Session["id"];
            int level = (int)Session["level"];
            if(level >= 2) {
                var data = (from i in db.paperquestion
                            where !(from j in db.exam_paper where j.exam.Id == id select j.paper.id).Contains(i.id)
                            select i).ToArray();
                var json = new {
                    total = data.Count(),
                    rows = data
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            } else {
                var data = (from i in db.paperquestion
                            where !(from j in db.exam_paper where j.exam.Id == id select j.paper.id).Contains(i.id)
                            && (i.isopen == true || i.admin_tea == tid)
                            select i).ToArray();
                var json = new {
                    total = data.Count(),
                    rows = data
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            }
        }
        [HttpPost]
        public ActionResult Add_Exam(exam inpt,long[] class_ids) {
            var ainpt = db.exam.Add(inpt);
            string excep = "";
            try { db.SaveChanges(); }
            catch (DbEntityValidationException ex) {
                excep = tf.AnalyseError(ex);
            }
            if(excep == "") {
                var examclass = new List<exam_class>();
                for (int i = 0; i < class_ids.Count(); i++) {
                    var excl = new exam_class
                    {
                        @class = db.@class.Find(class_ids[i]),
                        exam = ainpt
                    };
                    examclass.Add(excl);
                }
                db.exam_class.AddRange(examclass);
                try { db.SaveChanges(); }
                catch (DbEntityValidationException ex) {
                    excep = tf.AnalyseError(ex);
                }
                return Content(excep);
            } else {
                return Content(excep);
            }
        }
        public ActionResult Make_Exam(long[] idlist,long eid) {
            var exam = db.exam.Find(eid);
            List<exam_paper> li = new List<exam_paper>();
            foreach(long pid in idlist) {
                var paper = db.paper.Find(pid);
                var item = new exam_paper
                {
                    exam = exam,
                    paper = paper
                };
                li.Add(item);
            }
            if (li.Count() > 1) {
                db.exam_paper.AddRange(li);
            } else {
                db.exam_paper.Add(li[0]);
            }
            int result = li.Count();
            try { db.SaveChanges(); }
            catch (DbEntityValidationException e) {
                result = -1;
            }
            return Content(result.ToString());
        }
        [HttpPost]
        public ActionResult Update_Exam(exam inpt, long ecid) {
            var res = "";
            var examclass = db.exam_class.Find(ecid);
            examclass.exam.end_time = inpt.end_time;
            examclass.exam.start_time = inpt.start_time;
            examclass.exam.name = inpt.name;
            examclass.exam.attention = inpt.attention;
            examclass.exam.details = inpt.details;
            foreach (var item in db.timeless.Where(x => x.exam_class.Id == ecid)) {
                item.time = inpt.test_time-(examclass.exam.test_time-item.time);
            }
            examclass.exam.test_time = inpt.test_time;
            db.Entry(examclass.exam).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch(DbEntityValidationException e) {
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        public ActionResult Practice(long? id) {
            int level = (int)Session["level"];
            if (id == null) {
                if (level >= 2) {
                    return RedirectToAction("AdminPractice");
                }
                id = (long)Session["id"];
            } else {
                ViewBag.id = id;
            }
            ViewBag.id = id;
            ViewBag.name = db.teacher_info.Find(id).tea_name;
            ViewBag.classes = (from c in db.classinfo where c.tea_id == id select c).ToList();
            return View();
        }
        public ActionResult AdminPractice() {
            int level = (int)Session["level"];
            if (level < 2) {
                return RedirectToAction("Exam");
            } else {
                ViewBag.level = level;
                long id = (long)Session["id"];
                string cname = (string)Session["college"];
                ViewBag.id = id;
                ViewBag.name = db.teacher_info.Find(id).tea_name;
                if (level == 2) {
                    var li = (from i in db.teainfo where i.college_name == cname select i).ToList();
                    ViewBag.Toptions = new SelectList(li, "id", "tea_name");
                } else {
                    var li = db.teainfo.ToList();
                    var Toption = new SelectList(li, "id", "tea_name");
                    ViewBag.Toptions = new SelectList(li, "id", "tea_name");
                }
                return View();
            }
        }
        [HttpPost]
        public JsonResult PracticeClass_info(long? id) {
            if (id == null) {
                id = (long)Session["id"];
            }
            var data = (from i in db.practiceclass where i.tea_id == id select i).ToArray();
            var json = new {
                total = data.Count(),
                rows = data
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult PracticeAdmin_info() {
            int level = (int)Session["level"];
            string cname = (string)Session["college"];
            long cid = db.college_info.FirstOrDefault(u => u.college_name == cname).id;
            if (level == 2) {
                var data = (from i in db.practiceclass where i.college_id == cid select i).ToList();
                var json = new {
                    total = data.Count(),
                    rows = data
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            } else {
                var data = db.practiceclass.ToList();
                var json = new {
                    total = data.Count(),
                    rows = data
                };
                return Json(json, JsonRequestBehavior.AllowGet);
            }
        }
        [HttpPost]
        public ActionResult Del_Exam(long id){
            var exam = db.exam.Find(id);
            exam.isdelete = true;
            db.Entry(exam).State = EntityState.Modified;
            var res = "";
            try { db.SaveChanges(); }
            catch(DbEntityValidationException e){
                res = tf.AnalyseError(e);
            }
            return Content(res);
        }
        [HttpPost]
        public ActionResult Del_ExamPaper(long id) {
            var exampaper = db.exam_paper.Find(id);
            var ecids = from i in db.exam_class where i.exam.Id == exampaper.exam.Id select i.Id;
            var pqids = from i in db.paper_ques where i.paper.id == exampaper.paper.id select i.id;
            var stu_log = from i in db.stu_ans_log
                          where pqids.Contains(i.paper_ques.id)
                          && ecids.Contains(i.exam_class.Id)
                          select i;
            if (stu_log.FirstOrDefault() != null) { 
                db.stu_ans_log.RemoveRange(stu_log);
            }
            db.exam_paper.Remove(exampaper);
            var res = "";
            try { db.SaveChanges(); }
            catch(System.Exception e) {
                res = e.Message;
            }
            return Content(res);
        }
        public ActionResult Prac_feedback(long id) {
            var data = from i in db.practiceclass where i.Id == id select i;
            long teaid = (long)data.First().tea_id;
            long nid = (long)Session["id"];
            ViewBag.ecid = data.First().Id;
            if(data == null || !tf.HasAuthority(nid,teaid)) {
                View("Error");
            }
            var stuli = from i in db.exam_class.Find(id).@class.stu_class select i.stu_info;
            var pqlist = new List<SelectListItem>();
            var ndata = from i in db.feedback where i.ec_id == id select i;
            var ques_id = (from i in ndata
                           group i by i.pq_id into g
                           select g.Key).ToArray();
            for(var i = 0; i < ques_id.Length; i++) {
                var item = new SelectListItem{ Text = $"{i+1}", Value = $"{ques_id[i]}" };
                pqlist.Add(item);
            }
            ViewBag.QuesOptions = new SelectList(pqlist, "Value", "Text");
            ViewBag.SnameOptions = new SelectList(stuli, "id", "name");
            return View(data);
        }
        public JsonResult Prac_feedback_info(long id) {
            var data = from i in db.feedback where i.ec_id == id select i;
            var ques_id = (from i in data
                       group i by i.pq_id into g select g.Key).ToArray();
            var dataa = from i in data where i.anscount == 1 select i;
            var datab = from i in data where i.anscount >= 2 && i.anscount < 5 select i;
            var datac = from i in data where i.anscount >= 5 select i;
            var sa = new List<int>();//答题次数1
            var sb = new List<int>();//答题次数2-5
            var sc = new List<int>();//答题次数5以上
            int num = 0;
            var questions = new List<string>();
            foreach(long key in ques_id) {
                num++;
                var a_count = (from i in dataa where i.pq_id == key select i).Count();
                var b_count = (from i in datab where i.pq_id == key select i).Count();
                var c_count = (from i in datac where i.pq_id == key select i).Count();
                sa.Add(a_count);
                sb.Add(b_count);
                sc.Add(c_count);
                questions.Add(num.ToString());
            }
            var stu_ids = (from i in data group i by i.stu_id into g select g.Key).ToArray();
            int[] stu_num = new int[3] { 0, 0, 0 };
            foreach(long key in stu_ids) {
                var q_count = (from i in data where i.stu_id == key && i.anscount != null select i).Count();
                if(q_count == 0) {
                    stu_num[2]++;//未作答
                }else if(q_count == ques_id.Length) {
                    stu_num[1]++;//已完成作答
                } else {
                    stu_num[0]++;//正在作答
                }
            }
            var json = new {
                sCategories = questions,
                sa,
                sb,
                sc,
                num = stu_num
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult Prac_feedback_detail(long ecid, long stuid,long pqid) {
            var data = from i in db.feedback where i.ec_id == ecid select i;
            var stuli = (from i in data where i.stu_id == stuid select i.anscount).ToArray();
            for(var i = 0; i < stuli.Length; i++) {
                if(stuli[i] == null) {
                    stuli[i] = 0;
                }
            }
            var pqrows = (from i in db.stu_ans_log
                          where i.exam_class.Id == ecid && i.paper_ques.id == pqid && i.stu_info.id == stuid
                          select new { i.ans, i.cost, i.wronginfo, i.score }).ToArray();
            var pqinfo = new {
                total = pqrows.Length,
                rows = pqrows
            };
            var json = new {
                times = stuli,
                pqinfo
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        public ActionResult Exam_feedback(long id) {
            var data = from i in db.examclass where i.Id == id select i;
            long teaid = (long)data.First().tea_id;
            long nid = (long)Session["id"];
            ViewBag.ecid = data.First().Id;
            if (data == null || !tf.HasAuthority(nid, teaid)) {
                View("Error");
            }
            var stuli = from i in db.exam_class.Find(id).@class.stu_class select i.stu_info;
            var pqlist = new List<SelectListItem>();
            var ndata = from i in db.examfeedback where i.ec_id == id select i;
            var eid = db.exam_class.FirstOrDefault(u => u.Id == id).exam.Id;
            var pid = (from i in db.exam_paper where i.exam.Id == eid select i.paper.id).ToArray();
            var pq_id = new List<long>();
            for (int j = 0; j < pid.Count(); j++) {
                var paperid = pid[j];
                var pqid = (from i in db.paper_ques where i.paper.id == paperid select i.id).ToArray();
                for (int k = 0; k < pqid.Count(); k++) {
                    pq_id.Insert(pq_id.Count, pqid[k]);
                }    
            } 
            /*var ques_id = (from i in ndata
                           group i by i.pq_id into g
                           select g.Key).ToArray(); */
            for(var i = 0; i < pq_id.Count(); i++) {
                var item = new SelectListItem { Text = $"{i + 1}", Value = $"{pq_id[i]}" };
                pqlist.Add(item);
            }
            List<SelectListItem> typelist = new List<SelectListItem>()
            {
                new SelectListItem(){Text="请选择", Value="",Selected = true},
                new SelectListItem(){Text="全部", Value="0",Selected = false},
                new SelectListItem(){Text="错误", Value="1",Selected = false},
                new SelectListItem(){Text="正确", Value="2",Selected = false},
            };
            ViewBag.QuesOptions = new SelectList(pqlist, "Value", "Text");
            ViewBag.SnameOptions = new SelectList(stuli, "id", "name");
            ViewBag.TypeOptions = new SelectList(typelist, "Value", "Text");
            return View(data);
        }
        public JsonResult Exam_feedback_info(long id) {
            var data = from i in db.examfeedback where i.ec_id == id select i;
            var ques_id = (from i in data
                           group i by i.pq_id into g
                           select g.Key).ToArray();
            var dataa = from i in data where i.score == 10 select i;
            var datab = from i in data where i.score >= 1 && i.score < 10 select i;
            var datac = from i in data where i.score == 0 select i;
            var sa = new List<int>();//得分10分
            var sb = new List<int>();//得分1-9分
            var sc = new List<int>();//0分
            int num = 0;
            var questions = new List<string>();
            foreach (long key in ques_id) {
                num++;
                var a_count = (from i in dataa where i.pq_id == key select i).Count();
                var b_count = (from i in datab where i.pq_id == key select i).Count();
                var c_count = (from i in datac where i.pq_id == key select i).Count();
                sa.Add(a_count);
                sb.Add(b_count);
                sc.Add(c_count);
                questions.Add(num.ToString());
            }
            var stu_ids = (from i in data group i by i.stu_id into g select g.Key).ToArray();
            int[] stu_num = new int[2] { 0, 0};
            foreach (long key in stu_ids) {
                var q_count = (from i in data where i.stu_id == key && i.score != null select i).Count();
                if (q_count == 0) {
                    stu_num[1]++;//未作答
                } else {
                    stu_num[0]++;//已作答
                }
            }
            var json = new {
                sCategories = questions,
                sa,
                sb,
                sc,
                num = stu_num
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult Exam_feedback_ques(long ecid, long pqid, int type) {
            var data = from i in db.examfeedback where i.ec_id == ecid select i;
            var stuli = (from i in data select i.score).ToArray();
            for (var i = 0; i < stuli.Length; i++) {
                if (stuli[i] == null) {
                    stuli[i] = 0;
                }
            }
            var costli = (from i in data select i.cost).ToArray();
            var costs = new List<string>();
            for (var i = 0; i < costli.Length; i++) {
                if (costli[i] == null) {
                    costli[i] = TimeSpan.FromSeconds(0);
                }
                costs.Add(costli[i].ToString());
            }
            var pqrows = (from i in db.stu_ans_log
                          where i.exam_class.Id == ecid && i.paper_ques.id == pqid
                          orderby i.wronginfo
                          select new { i.stu_info.name, i.stu_info.stu_num, i.ans, cost = i.cost.ToString(), i.wronginfo, i.score }).ToArray();
            if (type == 1) {     // 错误答案
                pqrows = (from i in pqrows
                          where i.wronginfo != "满分" && i.wronginfo !="congratulations!!"
                          select i).ToArray();
            } else if (type == 2) {          // 正确答案
                pqrows = (from i in pqrows
                          where i.wronginfo == "满分" || i.wronginfo == "congratulations!!"
                          select i).ToArray();
            }
            var pqinfo = new {
                total = pqrows.Length,
                rows = pqrows
            };
            var json = new {
                score = stuli,
                cost = costs,
                pqinfo
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult Exam_feedback_stu(long ecid, long stuid, long pqid) {
            var data = from i in db.examfeedback where i.ec_id == ecid && i.stu_id == stuid select i;
            var stuli = (from i in data select i.score).ToArray();
            for (var i = 0; i < stuli.Length; i++) {
                if (stuli[i] == null) {
                    stuli[i] = 0;
                }
            }
            var costli = (from i in data select i.cost).ToArray();
            var costs = new List<string>();
            for (var i = 0; i < costli.Length; i++) {
                if (costli[i] == null) {
                    costli[i] = TimeSpan.FromSeconds(0);
                }
                costs.Add(costli[i].ToString());
            }
            var pqrows = (from i in db.stu_ans_log
                          where i.exam_class.Id == ecid && i.paper_ques.id == pqid && i.stu_info.id == stuid
                          select new { i.ans, cost = i.cost.ToString(), i.wronginfo, i.score }).ToArray();
            var pqinfo = new {
                total = pqrows.Length,
                rows = pqrows
            };
            var json = new {
                score = stuli,
                cost = costs,
                pqinfo
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }

        public ActionResult Notice() {
            return View();
        }
        public ActionResult Apply() {
            return View();
        }
        [HttpPost]
        public JsonResult Get_applyinfo() {
            var data = (from i in db.userapply where i.isdelete == false && i.isagree == false select i).ToArray();
            var json = new {
                total = data.Count(),
                rows = data
            };
            return Json(json, JsonRequestBehavior.AllowGet);
        }
        public ActionResult Agree_Apply(long id) {
            var apply = db.userapply.Find(id);
            var res = "";
            if (apply.isdelete == true) {
                res = "该申请记录已处理";
                return Content(res);
            } else {
                var colleges = (from i in db.college_info where i.college_name == apply.cname select i).ToArray();
                if(colleges.Length > 0) {
                    res = "该院校已有注册校级管理员";
                    apply.isdelete = true;
                    db.Entry(apply).State = EntityState.Modified;
                    db.SaveChanges();
                    Page_Load1(apply);
                    return Content(res);
                }else {
                    apply.isagree = true;
                    var college = new college_info()
                    {
                        college_name = apply.cname.TrimEnd(),
                    };
                    db.college_info.Add(college);
                    try { db.SaveChanges(); }
                    catch(DbEntityValidationException e) {
                        res = tf.AnalyseError(e);
                        return Content(res);
                    }
                    college = (from i in db.college_info where i.college_name == apply.cname select i).First();
                    var taccount = new teacher_info()
                    {
                        tea_level = 2,
                        tea_user = apply.account,
                        tea_name = apply.tname,
                        pass = "123456",
                        college_id = college.id,
                    };
                    db.teacher_info.Add(taccount);
                    try { db.SaveChanges(); }
                    catch (DbEntityValidationException e) {
                        res = tf.AnalyseError(e);
                    }
                    Page_Load2(apply);
                    return Content(res);
                }
            }
        }
        private void Page_Load1(userapply apply) //不一定是Load事件
       {

            Message jmessage = new jmail.Message();
            jmessage.Charset = "gbk";
            jmessage.Encoding = "base64";

            jmessage.From = "help_database@163.com";   //发信地址
            jmessage.FromName = "数据库在线实验平台";   //发信人

            jmessage.ReplyTo = "help_database@163.com";   //回复地址

            jmessage.Subject = "【数据库在线实验平台】您的申请已被处理"; //邮件标题
            jmessage.AddRecipient(apply.email, "", ""); //为收信人地址，后面两个参数可为空！

            //AddRecipient(emailAddress,recipientName,PGPKey) 
            //给邮件添加一个收件人。RecipientName和PGPKey是可选项，RecipientName为收件人姓名，PGPKey给邮件加密。

            jmessage.Body = "您的学校已有校级管理员，申请未被批准"; //信件内容

            jmessage.MailServerUserName = "help_database@163.com";   //为发信邮箱登陆的用户名
            jmessage.MailServerPassWord = "data123";   //为发信邮箱登陆的密码

            jmessage.Send("smtp.163.com", false); //IP为邮件服务器
            jmessage.Close();

        }
        private void Page_Load2(userapply apply) //不一定是Load事件
       {

            Message jmessage = new jmail.Message();
            jmessage.Charset = "gbk";
            jmessage.Encoding = "base64";

            jmessage.From = "help_database@163.com";   //发信地址
            jmessage.FromName = "数据库在线实验平台";   //发信人

            jmessage.ReplyTo = "help_database@163.com";   //回复地址

            jmessage.Subject = "【数据库在线实验平台】您的申请已被处理"; //邮件标题
            jmessage.AddRecipient(apply.email, "", ""); //为收信人地址，后面两个参数可为空！

            //AddRecipient(emailAddress,recipientName,PGPKey) 
            //给邮件添加一个收件人。RecipientName和PGPKey是可选项，RecipientName为收件人姓名，PGPKey给邮件加密。

            jmessage.Body = "恭喜您！您已成为校级管理员，密码为：123456"; //信件内容

            jmessage.MailServerUserName = "help_database@163.com";   //为发信邮箱登陆的用户名
            jmessage.MailServerPassWord = "data123";   //为发信邮箱登陆的密码

            jmessage.Send("smtp.163.com", false); //IP为邮件服务器
            jmessage.Close();

        }
        public ActionResult Refuse_Apply(long id) {
            var apply = db.userapply.Find(id);
            var res = "";
            apply.isdelete = true;
            apply.isagree = false;
            db.Entry(apply).State = EntityState.Modified;
            try { db.SaveChanges(); }
            catch(Exception e) {
                res = e.InnerException.InnerException.Message;
            }
            Page_Load1(apply);
            return Content(res);
        }

        public JsonResult Qb_example(long pqid) {
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
    }
}