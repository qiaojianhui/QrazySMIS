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
        DefaultPieDataset dpd=new DefaultPieDataset(); //����һ��Ĭ�ϵı�ͼ
        dpd.setValue("XXҩƷ1", 25);  //��������
        dpd.setValue("XXҩƷ2", 25);
        dpd.setValue("XXҩƷ3", 85);
        dpd.setValue("XXҩƷ4", 10);
        dpd.setValue("XXҩƷ5", 30);
        
        //���Բ�����API�ĵ�,��һ�������Ǳ��⣬�ڶ���������һ�����ݼ���������������ʾ�Ƿ���ʾLegend�����ĸ�������ʾ�Ƿ���ʾ��ʾ�������������ʾͼ���Ƿ����URL
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
        //���ñ�������  
        standardChartTheme.setExtraLargeFont(new Font("Simsun",Font.BOLD,20));  
        //����ͼ��������  
        standardChartTheme.setRegularFont(new Font("Simsun",Font.PLAIN,15));  
        //�������������  
        standardChartTheme.setLargeFont(new Font("Simsun",Font.PLAIN,15));  
        
        ChartFactory.setChartTheme(standardChartTheme);  
        JFreeChart chart=ChartFactory.createPieChart("����ͳ��ͼ",dpd,true,true,false); 
        //Ӧ��������ʽ  
        ChartFactory.setChartTheme(standardChartTheme); 
        ChartFrame chartFrame=new ChartFrame("",chart); 
        //chartҪ����Java��������У�ChartFrame�̳���java��Jframe�ࡣ�õ�һ�������������Ƿ��ڴ������Ͻǵģ��������м�ı��⡣
        chartFrame.pack(); //�Ժ��ʵĴ�Сչ��ͼ��
        chartFrame.setVisible(true);//ͼ���Ƿ�ɼ�
        
    }
}
