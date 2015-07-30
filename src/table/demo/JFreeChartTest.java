package table.demo;


import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChartTest
{
    public static void main(String[] args)
    {
        DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
        dpd.setValue("XX药品1", 25);  //输入数据
        dpd.setValue("XX药品2", 25);
        dpd.setValue("XX药品3", 85);
        dpd.setValue("XX药品4", 10);
        dpd.setValue("XX药品5", 30);
        
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
        //设置标题字体  
        standardChartTheme.setExtraLargeFont(new Font("Simsun",Font.BOLD,20));  
        //设置图例的字体  
        standardChartTheme.setRegularFont(new Font("Simsun",Font.PLAIN,15));  
        //设置轴向的字体  
        standardChartTheme.setLargeFont(new Font("Simsun",Font.PLAIN,15));  
        
        ChartFactory.setChartTheme(standardChartTheme);  
        JFreeChart chart=ChartFactory.createPieChart("销售统计图",dpd,true,true,false); 
        //应用主题样式  
        ChartFactory.setChartTheme(standardChartTheme); 
        ChartFrame chartFrame=new ChartFrame("",chart); 
        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
        chartFrame.pack(); //以合适的大小展现图形
        chartFrame.setVisible(true);//图形是否可见
        
    }
}
