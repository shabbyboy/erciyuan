using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace ObserveModel
{

    /// <summary>
    /// 原则：1、针对接口编程，不针对实现继承；2、多用组合，少用类继承；3、松耦合
    /// weatherdata 的基类
    /// </summary>
    public interface Subject
    {
        void RegisterObserver(Observer o);
        void RemoveObserver(Observer o);
        void NotifyObserver();
    }
    /// <summary>
    /// 观察者更新接口
    /// </summary>
    public interface Observer
    {
        void Update(float huminity, float pressure, float temperature);
    }
    /// <summary>
    /// 发布版显示接口
    /// </summary>
    public interface DisplayElement
    {
        void Display();
    }
    /// <summary>
    /// weatherData 模拟
    /// </summary>
    public class WeatherData : Subject
    {
        private ArrayList ObserverList;
        private float temperature{get;set;}

        //public float temperature
        //{
        //    get { return temperature; }
        //    set { temperature = value; }
        //}
        private float huminity{get;set;}
        //public float huminity
        //{
        //    get { return huminity; }
        //    set { huminity = value; }
        //}
        private float pressure{get;set;}
        //public float pressure
        //{
        //    get { return pressure; }
        //    set { pressure = value; }
        //}

        public WeatherData()
        {
            ObserverList = new ArrayList();
        }
        /// <summary>
        /// 注册新观察者
        /// </summary>
        /// <param name="o">传入的observer对象</param>
        public void RegisterObserver(Observer o)
        {
            ObserverList.Add(o);
        }
        /// <summary>
        /// 移除观察者
        /// </summary>
        /// <param name="o">需要移除的观察者对象</param>
        public void RemoveObserver(Observer o)
        {
            int i = ObserverList.IndexOf(0);
            if (i > 0)
            {
                ObserverList.Remove(i);
            }
        }
        /// <summary>
        /// 更新观察者数据
        /// </summary>
        public void NotifyObserver()
        {
            for (int i = 0; i < ObserverList.Count; i++)
            {
                Observer ob = ObserverList[i] as Observer;
                ob.Update(huminity, pressure, temperature);
                
            }

        }
        /// <summary>
        /// 当主题数据改变时，调用此函数
        /// </summary>
        public void MessureChanged()
        {
            NotifyObserver();
        }
        /// <summary>
        /// 模拟气象台发布数据
        /// </summary>
        /// <param name="temp">温度</param>
        /// <param name="humi"></param>
        /// <param name="pres">压力</param>
        public void SetWeatherData(float temp, float humi, float pres)
        {
            this.temperature = temp;
            this.huminity = humi;
            this.pressure = pres;
            MessureChanged();
        }

    }
    /// <summary>
    /// 当前状态发布板
    /// </summary>
    public class CurrentObserver : Observer, DisplayElement
    {
        private WeatherData wtdata;
        private float huminity;
        private float pressure;
        private float temperature;
        /// <summary>
        /// 初始化发布版，并注册为观察者
        /// </summary>
        /// <param name="wtdata"></param>
        public CurrentObserver(Subject wtdata)
        {
            this.wtdata = wtdata as WeatherData;
            wtdata.RegisterObserver(this);
        }
        /// <summary>
        /// 更新数据，接受来自主题的最新数据
        /// </summary>
        /// <param name="huminity"></param>
        /// <param name="pressure"></param>
        /// <param name="temperature"></param>
        public void Update(float huminity,float pressure,float temperature)
        {
            this.huminity = huminity;
            this.pressure = pressure;
            this.temperature = temperature;
            Display();
 
        }
        /// <summary>
        /// 显示发布板
        /// </summary>
        public void Display()
        {
            Console.WriteLine("currenttime:huminty={0},pressure = {1},temperature = {2}",huminity,pressure,temperature);
 
        }
 
    }
    class Program
    {
        static void Main(string[] args)
        {
            WeatherData wd = new WeatherData();
            CurrentObserver co = new CurrentObserver(wd);

            wd.SetWeatherData(80, 90, 5);
            wd.SetWeatherData(2, 10, 30);
            Console.ReadKey();
        }
    }
}
