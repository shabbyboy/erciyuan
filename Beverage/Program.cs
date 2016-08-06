using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Beverage
{
    #region 抽象类
    /// <summary>
    /// 基类
    /// </summary>
    public abstract class Beverage
    {
        public string description = "unknow bverage";

        public virtual void GetDescription()
        {
            Console.WriteLine(description); 

        }
        public abstract double Cost();
    }
    /// <summary>
    /// 饮料组件类
    /// </summary>
    public abstract class Component : Beverage
    {
        public override abstract void GetDescription();
    }

    #endregion


    #region 饮料类被装饰者

    /// <summary>
    /// Espresso 咖啡
    /// </summary>
    public class Espresso : Beverage
    {
        public Espresso()
        {
            description = "Espresso";
        }
        public override double Cost()
        {
            return 1.99;
        }
    }

    public class HouseBlend : Beverage
    {
        public HouseBlend()
        {
            description = "HouseBlend";
        }
        public override double Cost()
        {
            return 2.01;
        }
    }
    #endregion

    #region 修饰者 调料

    public class Monca : Component
    {
        Beverage beverage;

        public Monca(Beverage beverage)
        {
            description = "monca";
            this.beverage = beverage;
        }
        public override void GetDescription()
        {
            Console.WriteLine(beverage.description + description);
        }
        public override double Cost()
        {
            return beverage.Cost()+0.71;
        }
    }
    public class Ice : Component
    {
        Beverage beverage;
        public Ice(Beverage beverage)
        {
            description = "ice";
            this.beverage = beverage;
        }
        public override void GetDescription()
        {
            beverage.GetDescription();
            Console.WriteLine(description);
        }
        public override double Cost()
        {
            return 0.56 + beverage.Cost();
        }
    }
    #endregion 

    class Program
    {
       

        static void Main(string[] args)
        {
            Beverage beverage1 = new HouseBlend();
            Console.WriteLine("价格是{0}",beverage1.Cost());

            Beverage beverage2 = new HouseBlend();
            beverage2 = new Monca(beverage2);
            beverage2 = new Ice(beverage2);
            beverage2.GetDescription();
            Console.WriteLine("价格是:{0}",beverage2.Cost());
            Console.ReadKey();

        }
    }
}
