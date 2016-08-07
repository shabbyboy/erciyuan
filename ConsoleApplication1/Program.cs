using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CelLueModel
{
    /// <summary>
    /// Duck基类
    /// </summary>
    public abstract class Duck
    {
        #region 实体
        /// <summary>
        /// 行为接口变量
        /// </summary>
        /// 
        //private FlyBehavior flyBehavior;
        //public FlyBehavior flyBehavior 
        //{
        //    get { return this.flyBehavior; }
        //    set { this.flyBehavior = value; }
        //}

        //private QuackBehavior quackBehavior;
        //public QuackBehavior quackBehavior
        //{
        //    get { return this.quackBehavior; }
        //    set { this.quackBehavior = value; }
        //}

        public FlyBehavior flyBehavior { get; set; }
        public QuackBehavior quackBehavior { get; set; }
        #endregion

        

        #region 方法
        /// <summary>
        /// duck describe
        /// </summary>
        public abstract void Display();

        /// <summary>
        /// duck fly behavior
        /// </summary>
        public void PerformFly()
        {
            flyBehavior.fly();
        }
        /// <summary>
        /// duck quack behavior
        /// </summary>
        public void PerformQuack()
        {
            quackBehavior.quack();
        }
        /// <summary>
        /// duck swim behavior
        /// </summary>
        public void Swim()
        {
            Console.WriteLine("all duck can swim");
        }
        #endregion
    }
    /// <summary>
    /// mallduck 子类
    /// </summary>
    public class MallDuck : Duck
    {
        #region 变量
        
        #endregion

        #region 方法实现
        public MallDuck()
        {
            flyBehavior = new FlySwinds();
            quackBehavior = new QuackNoway();

        }
        
        public override void Display()
        {
            Console.WriteLine("i'm a green head duck");
        }

        
        #endregion
    }
    /// <summary>
    /// fly behavior interface
    /// </summary>
    public interface FlyBehavior
    {
        void fly();
    }
    /// <summary>
    /// extends interface flybehavior class fly behavior
    /// </summary>
    public class FlySwinds : FlyBehavior
    {
        public void fly()
        {
            Console.WriteLine("i can fly by winds");
        }
    }

    /// <summary>
    /// extends interface all kinds of behavior
    /// </summary>
    public class FlyNoWay : FlyBehavior
    {
        public void fly()
        {
            Console.WriteLine("i can't fly");
        }
    }
    /// <summary>
    /// interface duck quack behavior
    /// </summary>
    public interface QuackBehavior
    {
        void quack();
    }
    /// <summary>
    /// guagua jiao behavior
    /// </summary>
    public class QuackGua : QuackBehavior
    {
        public void quack()
        {
            Console.WriteLine("i can guagua jiao");
        }
    }
    /// <summary>
    /// sing behavior
    /// </summary>
    public class QuackNoway : QuackBehavior
    {
        public void quack()
        {
            Console.WriteLine("i can't sing");
        }
    }
    /// <summary>
    /// new modle duck for test at any time add new behavior
    /// </summary>
    public class ModelDuck : Duck
    {
        public ModelDuck()
        {
            quackBehavior = new QuackNoway();
            flyBehavior = new FlyNoWay();
        }
        public override void Display()
        {
            Console.WriteLine("this is a black duck");
         
        }
    }

    public class FlyByRockets : FlyBehavior
    {
        public void fly()
        {
            Console.WriteLine("i can fly by rockets boosting");
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            MallDuck mduck = new MallDuck();
            mduck.PerformFly();
            mduck.PerformQuack();
            //Console.ReadKey();

            ModelDuck modelduck = new ModelDuck();
            modelduck.PerformFly();
            //modelduck.PerformQuack();
            modelduck.flyBehavior = new FlyByRockets();
            modelduck.PerformFly();
            mduck.PerformFly();
            Console.ReadKey();

        }
    }
}
