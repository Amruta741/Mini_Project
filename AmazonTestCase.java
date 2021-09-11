package Project;

import java.util.*;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import jxl.Cell;
import jxl.Workbook;
import jxl.Sheet;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;


public class AmazonTestCase {

	private WebDriver driver;
	private String baseURL;
	private String uname;
	private String pass;
	private String item1=null;
	private String item2=null;
	private String item3=null;
	private String item4=null;
	private String item5=null;
	
	private String mod="Search Item";
	
	@Before
	public void setUp() throws Exception {
		
		Properties prop=new Properties();
		
		//accessing LoginDetail FIle
		prop.load(new FileInputStream("F:\\amruta\\WorkSpace\\MiniProject\\Configuration\\baseURL1.txt"));
		
		//getting amazon url
		baseURL=prop.getProperty("AmazonURl");
		
		//getting amazon user name
		uname=prop.getProperty("AmazonUname");
		
		//getting amamzon password
		pass=prop.getProperty("AmazonPass");
		
		//setting path of chroemdriver
		System.setProperty("webdriver.chrome.driver", "F:\\amruta\\Driver\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		
		//baseURL = "https://www.amazon.in/";
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		//window maximization
		driver.manage().window().maximize();
	}

	@Test
	public void test() throws InterruptedException, IOException {
		
		driver.get(baseURL);
		//driver.findElement(By.xpath(baseURL))
		
		driver.findElement(By.id("nav-link-accountList-nav-line-1")).click();
		
		//1. Sign In
		//Sign In Username
		driver.findElement(By.xpath("//*[@id=\'ap_email\']")).sendKeys(uname);
		driver.findElement(By.id("continue")).click();
		
		//Sign In Pass
		driver.findElement(By.xpath("//*[@id='ap_password']")).sendKeys(pass);
		driver.findElement(By.id("signInSubmit")).click();
		
		String module1="Module - Login";
		String res1="Result - Login Successfully";
		String cmt1="Comment - User logged In Successfully";
		
		//writeText(module,res,cmt);
		appendText(module1,res1,cmt1);
		
		String strFile = "F:\\amruta\\WorkSpace\\MiniProject\\DataPool\\ItemsAmazon.xls";
		String[] strItems = readAmazon(1,"Items",strFile);
		
		for(int i=1;i<strItems.length;i++)
		{
			//Clearing Search Textbox 
			driver.findElement(By.xpath(".//*[@id=\'twotabsearchtextbox\']")).clear();
			driver.findElement(By.xpath(".//*[@id=\'twotabsearchtextbox\']")).sendKeys(strItems[i]);
            driver.findElement(By.xpath(".//*[@id=\'nav-search-submit-button\']")).click();
			
        	//Printing Top 5 Items to TextFile
			System.out.println("");
			item1=driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[2]/div/span/div/div/div")).getText();
		
			System.out.println("");
			item2=driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[3]/div/span/div/div/div")).getText();
			
			System.out.println("");
			item3=driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[4]/div/span/div")).getText();
			
			System.out.println("");
			item4=driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[5]/div/span/div")).getText();
			
			System.out.println("");
			item5=driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[6]/div/span/div")).getText();
			
			String item=strItems[i];
			
			System.out.println("\n");
			appendText(item, "Result1",item1);
			
			System.out.println("\n");
			appendText(item, "Result2",item2);
			
			System.out.println("\n");
			appendText(item, "Result3",item3);

			System.out.println("\n");
			appendText(item, "Result4",item4);

			System.out.println("\n");
			appendText(item, "Result5",item5);

			System.out.println("\n");
			
			
			//Printing Top 5 Items to Console
			System.out.println("");
			System.out.println(driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[2]/div/span/div/div/div")).getText());
			
			System.out.println("");
			System.out.println(driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[3]/div/span/div/div/div")).getText());
			
			System.out.println("");
			System.out.println(driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[4]/div/span/div")).getText());
			
			System.out.println("");
			System.out.println(driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[5]/div/span/div")).getText());
			
			System.out.println("");
			System.out.println(driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[6]/div/span/div")).getText());

			Thread.sleep(3000);
		}
		
	}

	@After
	public void tearDown() throws Exception 
	{
		driver.close();
	}
	
	public static String[]  readAmazon (int row, String column, String strFilePath)
	{
		Cell c= null;
		int reqCol=0;
		int reqRow=0;
		WorkbookSettings ws = null;
		Workbook workbook = null;
		Sheet sheet = null;
		FileInputStream fs = null;
		try
		{
			fs = new FileInputStream(new File(strFilePath));
			ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			String[] data = null;

			// opening the work book and sheet for reading data
			workbook = Workbook.getWorkbook(fs, ws);
			sheet = workbook.getSheet(0);
			data = new String[sheet.getRows()];

			// Sanitise given data
			String col = column.trim();


			//loop for going through the given row
			for(int j=0; j<sheet.getColumns(); j++)
			{
				Cell cell = sheet.getCell(j,0);
				if((cell.getContents().trim()).equalsIgnoreCase(col))
				{
					reqCol= cell.getColumn();
					//System.out.println("column No:"+reqCol);  
					for (int i = 0; i < sheet.getRows(); i++)
					{
						c = sheet.getCell(reqCol, reqRow);
						data[i] = c.getContents();
						System.out.println(data[i]);
						fs.close();
						reqRow=reqRow+1;
					}
					return data;
				}
			}
		}
		catch(BiffException be)
		{
			System.out.println("The given file should have .xls extension.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("NO MATCH FOUND IN GIVEN FILE: PROBLEM IS COMING FROM DATA FILE");

		return null;
	}
	
	
	
	public static void writeText(String module,String res,String cmt)throws IOException
	{
		File f=new File("inputText.txt");
		FileWriter fw=new FileWriter(f);
		
		fw.write("\n\n"+module +"\n\n"+ res+"\n\n" + cmt);
		
		fw.close();
	}
	
	public static void appendText(String module,String res,String cmt)throws IOException
	{
		//File f=new File("login.txt");
		FileWriter fw=new FileWriter("inputText.txt",true);
		
		fw.write("\n\n"+module +"\n\n"+ res+"\n\n" + cmt);
		
		fw.close();
	}

}
