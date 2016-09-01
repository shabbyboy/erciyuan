using System;
using System.IO;
using System.Threading;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CW
{
    class FileReadAsynchronous
    {
        public Byte[] Buffer{get;set;}
        public int BufferSize { get; set;}
        public FileReadAsynchronous(int buffersize)
        {
            this.Buffer = new byte[buffersize];
            this.BufferSize = buffersize;
        }
        public void FileRead(string path)
        {
            Console.WriteLine("同步开始");
            using(FileStream filestream = new FileStream(path,FileMode.Open))
            {
                filestream.Read(Buffer, 0, BufferSize);
                String output = System.Text.Encoding.UTF8.GetString(Buffer);
                Console.WriteLine("读取信息：{0}",output);
                Console.WriteLine("同步编程结束");
            }
        }
        public void FileReadAsyn(string path) 
        {
            Console.WriteLine("异步开始");
            if (File.Exists(path))
            {
                FileStream fs = new FileStream(path, FileMode.Open);
                fs.BeginRead(Buffer, 0, BufferSize, UasyncCallBack, fs);
            }else
            {
                Console.WriteLine("文件不存在！");
            }
        }
        void UasyncCallBack(IAsyncResult ia)
        {
            Console.WriteLine();
            FileStream fs = ia.AsyncState as FileStream;
            if (fs != null)
            {
                Thread.Sleep(1000);
                fs.EndRead(ia);
                fs.Close();
                string output = System.Text.Encoding.UTF8.GetString(this.Buffer);
                Console.WriteLine("异步编程结束！");
            }
 
        }
        public static void Main(String[] args)
       {
            FileReadAsynchronous reader = new FileReadAsynchronous(1024);
            DelegateSync dsc = new DelegateSync();
            string abc = string.Empty;
            //改为自己的文件路径
           // string path = "D:\\c#\\ceshi.txt";
            //dsc.DownLoading(abc);
            dsc.BeginDownLoad(abc);
            //Console.WriteLine("开始读取文件了...");
            //reader.FileReadAsyn(path);
            //reader.FileRead(path);

            Console.WriteLine("我这里还有一大滩事呢.");
            reader.DoSomething();
            Console.WriteLine("终于完事了，输入任意键，歇着！");
            Console.ReadKey(); 
        }

        public void DoSomething()
        {
            Thread.Sleep(1000);
            for (int i = 0; i < 1000; i++)
            {
                if (i % 888 == 0)
                {
                    Console.WriteLine("888的倍数：{0}", i);
                }
            }
        }
    }
}
