using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;



namespace FactoryTest
{
    //#region
    ////工作流程
    //#endregion
    /////// <summary>
    /////// 对象接口类
    /////// </summary>
    ////public interface ICar
    ////{
    ////    void Speed(int i);
    ////}
    /////// <summary>
    /////// a车实例
    /////// </summary>
    ////public class Acar:ICar
    ////{
    ////    public void Speed(int i)
    ////    {
    ////        Console.WriteLine("the car's speed:{0}",i);
    ////    }
    ////}
    /////// <summary>
    /////// b车实例
    /////// </summary>
    ////public class Bcar : ICar
    ////{
    ////    public void Speed(int i)
    ////    {
    ////        Console.WriteLine("the car's speed:{0}",i);
    ////    }
    ////}
    /////// <summary>
    /////// 工厂
    /////// </summary>
    ////public class Factory
    ////{
    ////    public ICar getCarSpeed(string carname)
    ////    {
    ////        switch (carname)
    ////        {
    ////            case "acar": return new Acar();
    ////            case "bcar": return new Bcar(); 
    ////            default:
    ////                throw new Exception("该商店不存在");  
    ////        }

    ////    }
    ////}
    




    ///// <summary>
    ///// 对象接口类
    ///// </summary>
    //public interface ICar
    //{
    //    void Speed(int i);
    //}
    ///// <summary>
    ///// a车实例
    ///// </summary>
    //public class Acar : ICar
    //{
    //    public void Speed(int i)
    //    {
    //        Console.WriteLine("the car's speed:{0}", i);
    //    }
    //}
    ///// <summary>
    ///// b车实例
    ///// </summary>
    //public class Bcar : ICar
    //{
    //    public void Speed(int i)
    //    {
    //        Console.WriteLine("the car's speed:{0}", i);
    //    }
    //}
    ///// <summary>
    ///// 工厂接口类
    ///// </summary>
    //public interface IFactory
    //{
    //    ICar createCar();
    //}
    ///// <summary>
    ///// 继承工厂的实体类
    ///// </summary>
    //public class CreateAcar:IFactory
    //{
    //    public ICar createCar()
    //    {
    //        return new Acar();
    //    }
    //}
    ///// <summary>
    ///// 继承工厂的实体类
    ///// </summary>
    //public class CreateBcar:IFactory
    //{
    //    public ICar createCar()
    //    {
    //        return new Bcar();
    //    }
    //}

    class Program
    {

        static void Main(string[] args)
        {
         
        }
    }
}
