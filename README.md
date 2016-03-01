# CL_RSSParser  v 1.0
Java API to query craigslist site and parse results for use in a java application.

The purpose of this API is to allow your application code to query the craigslist 'For Sale' section and process the result set.
Using the API you can quickly and easily set up a craigslist search and trigger a request to receive the search results on demand.
A simple OO aproach allow you to process the results.

The parser works by scrapping the RSS version of the return results.
You can simple include the code to integrate into your JAVA applicaiton.


# How to Use

Set up a for ForSaleSearchResultGen object. This will initialize the search agent .
The second parameter indicates the section of the for sale search ( ex. 'all' , 'bikes', 'antiques')
You can include a proxy object if needed.

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("yourproxy.com", 8888));
		 // search generator for newyork antiques
		ForSaleSearchResultGen myGenerator2 = new ForSaleSearchResultGen(proxy, "newyork", "ata");

or no proxy
  	
  	ForSaleSearchResultGen myGenerator2 = new ForSaleSearchResultGen("newyork", "ata");


Once the search again is set up you can update it by setting a search term or the users entered search value.
The setSearch term method will return a value code indicating if it was successfull.
This will determine how you process the results or display a message to the users.
		
		int result = myGenerator2.setSearchTerm("BookShelf");
		//continue if SUCESS returned
		//else handle errors
		if(result != ForSaleSearchResultGen.SUCCESS) {
			//do some error handling for user if the URL creation did not work out
		}
		

You then call the search functionality. This seperate operation will allow you to trigger the search on a schedule if needed. 

				// else you can go ahead to test the search funciton		
    		int searchResult = myGenerator2.doSearch();
    		

After you trigger the search request you can call the	getSearchResults method to retrieve the latest results.
Again a return code will determine if the response was sucessfully processed.
Simply iterate the results and use the getter methods to access the results.

		// after you perform the search check the result code		
		if(searchResult == ForSaleSearchResultGen.SUCCESS) {
			//search is good continue
			SearchResult myResult = myGenerator2.getSearchResults();
			
			System.out.println(myResult.getTitle());
			System.out.println(myResult.getDescription());
			System.out.println(myResult.getLinkURL());

			Iterator <SearchResultItem> items = myResult.getResultItems().iterator();
			int count = 0;
			while(items.hasNext()){
				SearchResultItem curResult = (SearchResultItem)items.next();
				System.out.println("Result "+ ++count);
				System.out.println("	"+curResult.getTitle());
				System.out.println("	"+curResult.getDescription());
				System.out.println("	"+curResult.getResultURL());
				System.out.println("	"+curResult.getImageURL());
				System.out.println("	"+curResult.getDcDate());
				System.out.println("===================================");
			}
		} else {
			// based on the result code you can handle this error.
			if(result == ForSaleSearchResultGen.IOERROR){
			//handle errors returned form
			//result code 
			}
		}
    		
