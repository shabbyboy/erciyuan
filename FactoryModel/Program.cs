using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace FactoryModel
{
    /// <summary>
    /// 披萨的基类负责披萨的创作
    /// </summary>
    public abstract class Pizze
    {
        #region 变量
        public string name;
        public string dough;
        public string sauce;
        public ArrayList toppings = new ArrayList();
        #endregion
        #region 方法
        public void Prepare()
        {
        }
        public void Bake()
        {
        }
        public void Cut()
        {
            Console.WriteLine("切芝士披萨");
        }
       public void Box()
        {

        }
        public string GetName()
        {
            return name;
        }
        #endregion

    }
    /// <summary>
    /// 披萨商店负责创建新的批萨加盟店即我们所建的工厂
    /// </summary>
    public abstract class PizzeStore
    {
        public Pizze OrderPizze(String type)
        {
            Pizze pizze;
            pizze = CreatePizze(type);

            pizze.Prepare();
            pizze.Bake();
            pizze.Cut();
            pizze.Box();
            return pizze;
        }
        public abstract Pizze CreatePizze(string type);
     
    }
    /// <summary>
    /// 披萨加盟店
    /// </summary>
    public class NyPizze : PizzeStore
    {
        public override Pizze CreatePizze(string type)
        {
            if (type == "chess")
            {
                return new ChessPizze();
            }
            else { return null; }
        }
    }
    public class BjPizze : PizzeStore
    {
        public override Pizze CreatePizze(String type)
        {
            if (type == "chess")
            {
                return new ChessPizze();
            }
            else { return null; }
        } 
    }
/// <summary>
/// chess口味的披萨
/// </summary>
    public class ChessPizze : Pizze
    {
        public ChessPizze() 
        {
            base.name = "chesspizze";
            base.dough = "";
            base.sauce = "";
            base.toppings.Add(this);
        }
 
    }

    class Program
    {
        static void Main(string[] args)
        {
            PizzeStore nypizze = new NyPizze();
            Pizze pz = nypizze.OrderPizze("chess");
            Console.ReadKey();

        }
    }
}
