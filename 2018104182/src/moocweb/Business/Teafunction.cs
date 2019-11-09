using System;
using System.IO;
using System.Collections.Generic;
using System.Web.Script.Serialization;
using System.Linq;
using System.Web;
using System.Data;
using System.Text;
using System.Collections;
using Newtonsoft.Json;
using System.Data.Entity.Validation;
using System.Diagnostics;
using moocweb.Models;
using System.Threading;

namespace moocweb.Business
{
    public class Teafunction
    {
        private Entities db = new Entities();
        /*protected override void Dispose(bool disposing) {
            if (disposing) {
                db.Dispose();
            }
            base.Dispose(disposing);
        }*/
        public string DataTableToJsonWithStringBuilder(DataTable table) {
            var jsonString = new StringBuilder();
            if (table.Rows.Count > 0) {
                jsonString.Append("[");
                for (int i = 0; i < table.Rows.Count; i++) {
                    jsonString.Append("{");
                    for (int j = 0; j < table.Columns.Count; j++) {
                        if (j < table.Columns.Count - 1) {
                            jsonString.Append("\"" + table.Columns[j].ColumnName.ToString()
                         + "\":" + "\""
                         + table.Rows[i][j].ToString() + "\",");
                        } else if (j == table.Columns.Count - 1) {
                            jsonString.Append("\"" + table.Columns[j].ColumnName.ToString()
                         + "\":" + "\""
                         + table.Rows[i][j].ToString() + "\"");
                        }
                    }
                    if (i == table.Rows.Count - 1) {
                        jsonString.Append("}");
                    } else {
                        jsonString.Append("},");
                    }
                }
                jsonString.Append("]");
            }
            return jsonString.ToString();
        }
        public string DataTableToJsonWithJSSS(DataTable table) {
            JavaScriptSerializer jsSerializer = new JavaScriptSerializer();
            List<Dictionary<string, object>> parentRow = new List<Dictionary<string, object>>();
            Dictionary<string, object> childRow;
            foreach (DataRow row in table.Rows) {
                childRow = new Dictionary<string, object>();
                foreach (DataColumn col in table.Columns) {
                    childRow.Add(col.ColumnName, row[col]);
                }
                parentRow.Add(childRow);
            }
            return jsSerializer.Serialize(parentRow);
        }
        public string DataTableToJsonWithJsonNet(DataTable table) {
            string JsonString = string.Empty;
            JsonString = JsonConvert.SerializeObject(table);
            return JsonString;
        }
        public Hashtable DataTableToHashTable(DataTable table) {
            Hashtable hash = new Hashtable();
            for(int j = 0; j < table.Rows.Count; j++) {
                for (int i = 0; i < table.Columns.Count; i++) {
                    hash.Add(table.Columns[i].ColumnName, table.Rows[j][i]);
                }
            }
            return hash;
        }
        public string AnalyseError(DbEntityValidationException ex) {
            System.Text.StringBuilder errors = new System.Text.StringBuilder();
            IEnumerable<DbEntityValidationResult> validationResult = ex.EntityValidationErrors;
            foreach (DbEntityValidationResult result in validationResult) {
                ICollection<DbValidationError> validationError = result.ValidationErrors;
                foreach (DbValidationError err in validationError) {
                    errors.Append(err.PropertyName + ":" + err.ErrorMessage + "\r\n");
                }
            }
            return errors.ToString();
        }
        public string GenrateL(string[] judges,string[] values,string Dic) {
            var outpt = "";
            try {
                string path = Dic + "/template.l";
                var res = File.ReadAllLines(path).ToList();
                string[] keywords = new string[judges.Length];
                for (var i = 0; i < judges.Length; i++) {
                    keywords[i] = $"KEY{i} ({judges[i]})";
                }
                res.InsertRange(9, keywords);
                string[] sentences = new string[judges.Length];
                int index = 10 + judges.Length;
                for (var i = 0; i < judges.Length; i++) {
                    sentences[i] = $"{{KEY{i}}}    {{score+={values[i]};}}";
                }
                res.InsertRange(index, sentences);
                string npath = Dic +"/tmp.l";
                File.WriteAllLines(npath, res.ToArray());
            }catch(Exception e) {
                outpt = e.Message;
            }
            return outpt;
        }
        public string RunExe(string path,string inpt,string directory) {
            inpt = inpt.Trim();
            var output = "";
            using (Process process = new Process()) {
                ProcessStartInfo startInfo = new ProcessStartInfo(path, inpt);
                startInfo.WorkingDirectory = directory;
                startInfo.UseShellExecute = false;
                startInfo.CreateNoWindow = true;
                startInfo.RedirectStandardOutput = true;
                process.StartInfo = startInfo;
                process.Start();
                output = process.StandardOutput.ReadToEnd();
                process.WaitForExit();
            }
            return output;
        }
        public string CompileC(string exename,string Dic) {
            var clinpt = $"cl /Fo\"{Dic}/object/{exename}\" /Fe\"{Dic}/exe/{exename}.exe\" {Dic}/lex.yy.c";
            string output = "";
            using (Process process = new Process()) {
                process.StartInfo.FileName = "cmd.exe";
                process.StartInfo.CreateNoWindow = true;
                process.StartInfo.UseShellExecute = false;
                process.StartInfo.RedirectStandardOutput = true;
                process.StartInfo.RedirectStandardInput = true;
                process.Start();
                process.StandardInput.WriteLine(clinpt + "&exit");
                process.StandardInput.AutoFlush = true;
                process.StartInfo.UserName = "administrator";
                var pass = new System.Security.SecureString();
                string p = "!qaz@wsx3edc4rfv";
                foreach(char i in p) {
                    pass.AppendChar(i);
                }
                process.StartInfo.Password = pass;
                output = process.StandardOutput.ReadToEnd();
                process.WaitForExit();
            }
            return output;
        }
        public string RunFlex(string path,string inpt,string dic,string user) {
            var npath = $"{dic}/{user}/tmpsql{Thread.CurrentThread.ManagedThreadId}.txt";
            var opath = $"{dic}/{user}/result{Thread.CurrentThread.ManagedThreadId}.txt";
            Directory.CreateDirectory($"{dic}/{user}");
            File.WriteAllText(npath, inpt);
            try {
                using (Process process = new Process()) {
                    process.StartInfo = new ProcessStartInfo()
                    {
                        FileName = path,
                        WorkingDirectory = $"{dic}/{user}",
                        CreateNoWindow = true,
                        UseShellExecute = false,
                        Arguments = npath + " " + opath
                    };
                    process.Start();
                    process.WaitForExit();
                }
                var output = File.ReadAllText(opath);
                File.Delete(npath);
                File.Delete(opath);
                return output;
            }catch(Exception e) {
                //File.Delete(npath);
                //File.Delete(opath);
                return "0";
            }
        }
        public bool HasAuthority(long tid1,long tid2) {
            var teacher1 = db.teainfo.Find(tid1);
            var teacher2 = db.teainfo.Find(tid2);
            if(tid1 == tid2) {
                return true;
            }
            if(teacher1.tea_level == 3) {
                return true;
            }else if(teacher1.tea_level == 2){
                if(teacher1.college_name == teacher2.college_name) {
                    return true;
                }
            }
            return false;
        }
    }
}