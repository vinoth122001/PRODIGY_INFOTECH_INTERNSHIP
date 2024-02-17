package com.intern;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class WebScraper extends ScraperFrame {

    static JOptionPane dialogBox = new JOptionPane();                  //JOptionPane Used to Display the msg;
    static String relativeUrl="";
    static int limit=0;                                              // to limit the number of webpages to scrape
    static String baseUrl = "https://www.flipkart.com";             // Used since the <href> form the webpages doesn't include this

//Contains-Web Scraper and Crawler Logic:
    public static void scrapeProductPage(List<ProductBean> productList,Set<String> pagesDiscovered, List<String> pagesToScrape) {
        
        if (!pagesToScrape.isEmpty()) {
            
            String url = pagesToScrape.remove(0); //Used to remove the current page which is Scraped 

            pagesDiscovered.add(url);                  //Add that Scraped page to Discovered pages

            Document doc;                           // initializing the HTML Document page variable and fetching the target website

            try {                                    
                doc = Jsoup
                        .connect(url)         //https://www.useragentstring.com - Useragent lets servers and network peers identify the application,os,vendors of the requesting user agent.
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36 Edg/121.0.0.0")
                        .get();
            } catch (IOException e) {
                dialogBox.showMessageDialog(null, "Connection Error, Reset and Try Again ", "Error",JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }

            String productUrl="";                             //Used to append the relative page url with base Url

            Elements products = doc.select("div._4ddWXP");   
            
            for (Element product : products) {             // extracting the data from the product HTML element and store it in List<>
                ProductBean obj = new ProductBean();

                obj.setName((product.select("a.s1Q9rs").attr("title")).replace(",", ""));   
                obj.setPrice((product.select("div._30jeq3").text()).replace(",", "").replace("₹","Rs."));   //unicode for rupees symbol-'\u20B9'
                obj.setRating(product.select("div._3LWZlK").text());
                if(obj.getRating()==""){
                    obj.setRating(null);
                }

                productUrl=product.select("a.s1Q9rs").attr("href");    
                obj.setUrl(baseUrl+productUrl);                        //concatinating the url

               
                //System.out.println(obj.toString());            //Method Used to print the string
                    
                productList.add(obj);                    // adding obj to the list of the scraped products and used to export later
            }

        //Web Crawler Logic
            Elements paginationElements = doc.select("a.ge-49M");     // retrieving the list of pagination HTML element
            String pgUrl="";
            for (Element pageElement : paginationElements) {
                
                String pageUrl = pageElement.attr("href");          //Since href doesn't have the proper format so need to concatenate with baseUrl
                pgUrl = baseUrl + pageUrl;
                //System.out.println(pgUrl);
                if (!pagesDiscovered.contains(pgUrl) && !pagesToScrape.contains(pgUrl)) {

                    pagesToScrape.add(pgUrl);
                    pagesDiscovered.add(pgUrl);                  // adding the link just discovered to pagesDiscovered
                    break;                      

                }

            }
            
            System.out.println(url + " -> page scraped"); 
            
        }
    }
    

//Main Method
    public static void scrapePage(String url, int pages) {

        relativeUrl = url;
        limit = pages;
        
        List<ProductBean> productList = new ArrayList<>();       //list of Java object to store the scraped data

        Set<String> pagesDiscovered = new HashSet<>();          //set contains the Discovered Urls

        List<String> pagesToScrape = new ArrayList<>();        // list of urls to scrape
        
        //Computer Accessories - flipkart.com
       //String relativeUrl = "/computers";

        String URL = baseUrl + relativeUrl;
        System.out.println(URL);

        pagesToScrape.add(URL);
        //pagesToScrape.add("https://www.flipkart.com/computers");

        int i = 0;
                                                                   
        while (!pagesToScrape.isEmpty() && i < limit) {
            
            scrapeProductPage(productList, pagesDiscovered, pagesToScrape);   //Fn Call

            try {
                TimeUnit.MILLISECONDS.sleep(200);                    //adding a 200ms delay for avoid overloading the server
            } catch (InterruptedException e) {
                e.printStackTrace();
            }            

            i++;
        }

        exportToCSV(productList, "ProductData.csv");               //Created File Name

        dialogBox = new JOptionPane();                                      //JOptionPane Used to Display the Output msg
        dialogBox.showMessageDialog(null, "Scraping Done ! Data Exported in 'ProductData.csv' file ", "Completed",JOptionPane.INFORMATION_MESSAGE);
        
        System.out.println(productList.size()); 
    }


//Method To Export Data to CSV file format
    public static void exportToCSV(List<ProductBean>productList, String filePath){
        try {
            FileWriter writer = new FileWriter(filePath);
        //header
            writer.append("Name,Price,Rating,URL\n");
            
            for(ProductBean obj : productList){         //Used , as delimiter
                writer.append(String.join( ",",obj.getName(), String.valueOf(obj.getPrice()), obj.getRating(), obj.getUrl() )).append("\n");
            }
            System.out.println("Exported Successfully"); 
            writer.close();           
        } catch (Exception e) {
            System.out.println("Exception while exportToCSV"+ e);
        }
    }
}


 

    

