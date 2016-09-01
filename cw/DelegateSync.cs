using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CW
{
    public delegate string AysnDownloadDelegate(string filename);
    class DelegateSync
    {
        public string DownLoading(string filename)
        {
            string filester = string.Empty;
            Console.WriteLine("下载事件开始！");
            System.Threading.Thread.Sleep(1000);
            Random random = new Random();
            StringBuilder builder = new StringBuilder();
            int num;
            for (int i = 0; i < 100; i++)
            {
                num = random.Next(1000);
                builder.Append(i);
            }
            filester = builder.ToString();
            Console.WriteLine("事件结束！");
            return filester;
        }

        public IAsyncResult BeginDownLoad(string filename)
        {
            string filester = string.Empty;
            AysnDownloadDelegate downloaddelegate = new AysnDownloadDelegate(DownLoading);
            return downloaddelegate.BeginInvoke(filename, DownLoaded, downloaddelegate);
        }

        public void DownLoaded(IAsyncResult result)
        {
            AysnDownloadDelegate asd = result.AsyncState as AysnDownloadDelegate;
            if (asd  != null)
            {       
                string fileStr = asd.EndInvoke(result);
                if (!string.IsNullOrEmpty(fileStr))
                {
                    Console.WriteLine("下载文件：{0}", fileStr);
                }
                else
                {
                    Console.WriteLine("下载数据为空!");
                }
            }
            else
            {
                Console.WriteLine("下载数据为空!");
            }
 
        }
    }
}
