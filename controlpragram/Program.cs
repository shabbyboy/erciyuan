using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
//注意：委托与其绑定方法的参数必须一至，即当 Handler 所输入的参数为 object 类型，其绑定方法 GetMessage 的参数也必须
//为 object 。否则，即使绑定方法的参数为 object 的子类，系统也无法辨认。
//协变是针对返回值类型的：返回值输入委托返回值子类型的都可以使用委托，反之则不行，逆变是针对参数类型的：参数是委托参数子类型的都可以使用委托，反之则不行
namespace controlpragram
{
    /// <summary>
    /// 多广播委托实现观察者
    /// </summary>
    /// <param name="wage">参数</param>
    /// <returns></returns>
    public delegate double Handler(double wage);

    public class WageManager
    {
        private Handler eventHandler;
        public void registerWorker(Handler eventHandler1)
        {
            eventHandler += eventHandler1;
        }
        public void removeWorker(Handler eventHandler1)
        {
            eventHandler -= eventHandler1;
        }
        public void excuteWage(double wage)
        {
            if (eventHandler != null)
            {
                if (eventHandler.GetInvocationList().Count() != 0)
                    eventHandler(wage);
            }
        }

        static void Main(String[] args)
        {
            Handler wagehandler1 = new ManagerWorker().getWages;
            Handler wagehandler2 = new AssistantWorker().getWages;
            WageManager wmanager = new WageManager();
            wmanager.registerWorker(wagehandler1);
            wmanager.registerWorker(wagehandler2);
            wmanager.excuteWage(100);

        }


    }
    /// <summary>
    /// managerworkerguachazhe 
    /// </summary>
    public class ManagerWorker
    {
        public double getWages(double wage)
        {
            double doublewage = 1.5 * wage;
            Console.WriteLine("managerworker wage is :{0}", doublewage);
            return doublewage;
        }
    }
    /// <summary>
    /// assistant观察者
    /// </summary>
    public class AssistantWorker
    {
        public double getWages(double wage)
        {
            double doublewage = 2 * wage;
            Console.WriteLine("assistantworker wage is :{0}", doublewage);
            return doublewage;
        }
    }

    #region
    ///// <summary>
    ///// 观察者模式抽象类实现
    ///// </summary>
    //class WageManager
    //{
    //    IList<Worker> listworker = new List<Worker>();
    //    /// <summary>
    //    /// 注册添加观察者worker
    //    /// </summary>
    //    /// <param name="worker"></param>
    //    public void registerWorker(Worker worker)
    //    {
    //        listworker.Add(worker);
    //    }
    //    /// <summary>
    //    /// 移除观察者worker
    //    /// </summary>
    //    /// <param name="worker"></param>
    //    public void removeWorker(Worker worker)
    //    {
    //        listworker.Remove(worker);
    //    }
    //    /// <summary>
    //    /// 执行计算方法
    //    /// </summary>
    //    /// <param name="wage"></param>
    //    public void excute(double wage)
    //    {
    //        foreach (var worker in listworker)
    //        {
    //            worker.getWages(wage);
    //        }
    //    }



    //    static void Main(string[] args)
    //    {


    //        //WageManager wmanager = new WageManager();
    //        //ManagerWorker mworker = new ManagerWorker();
    //        //AssistantWorker aworker = new AssistantWorker();
    //        //wmanager.registerWorker(mworker);
    //        //wmanager.registerWorker(aworker);
    //        //wmanager.excute(100);

    //    }
    //}
    #endregion
    #region
    ///// <summary>
    ///// 程序入口
    ///// </summary>
    ///// <param name="args">入口程序参数</param>
    //static void Main(string[] args)
    //{
    //    //string dirfile = "dir/file";
    //    //string dir;
    //    //string file;
    //    //Program.SplitPath(dirfile, out dir, out file);
    //    //Console.WriteLine("{0},{1}",dir,file);
    //    //Console.ReadKey();
    //}
    ///// 分析文件路径
    ///// </summary>
    ///// <param name="path">文件路径</param>
    ///// <param name="dir">文件目录</param>
    ///// <param name="file">文件名</param>
    //static void SplitPath(string path, out string dir, out string file)
    //{
    //    int i = path.Length;
    //    while (i > 0)
    //    { 
    //        char c = path[i - 1];
    //        if (c == '/') break;
    //        i--;
    //    }
    //    dir = path.Substring(0, i);
    //    file = path.Substring(i);
    //}

    #endregion
    #region
    ///// <summary>
    ///// 观察者worker抽象类
    ///// </summary>
    //public abstract class Worker
    //{
    //    public abstract double getWages(double wage);
    //}
    ///// <summary>
    ///// managerwoker观察者
    ///// </summary>
    //public class ManagerWorker : Worker
    //{
    //    public override double getWages(double wage)
    //    {
    //        double doublewage = 1.5 * wage;
    //        Console.WriteLine("managerworker wage is :{0}", doublewage);
    //        return doublewage;
    //    }
    //}
    ///// <summary>
    ///// assistant观察者
    ///// </summary>
    //public class AssistantWorker : Worker
    //{
    //    public override double getWages(double wage)
    //    {
    //        double doublewage = 2 * wage;
    //        Console.WriteLine("assistantworker wage is :{0}", doublewage);
    //        return doublewage;
    //    }
    //}
    #endregion

}
