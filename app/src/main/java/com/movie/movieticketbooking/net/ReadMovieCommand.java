package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.xml.sax.SAXException;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.parsers.ParseResponse;
import com.movie.movieticketbooking.vos.MovieItems;

public class ReadMovieCommand extends JsonRequestCommand {
	
	private String rssFeed;
	private String theatreId;
	
	public ReadMovieCommand(String rssFeed,String theatreId) {
		this.rssFeed = rssFeed;
		this.theatreId = theatreId;
	}
	
	public List<MovieItems> execute() throws IOException, SAXException,JsonSyntaxException {
		Uri.Builder builder = Uri.parse(rssFeed).buildUpon();
		
		if(theatreId!=null)
		builder.appendQueryParameter("theaterid", theatreId);
		
		
		
		InputStream inStream = requestStream(builder.toString());
		Log.d("Movie"," Url formed is "+builder.toString());
		Log.d("Movie"," input stream formed is "+inStream.toString());
		//Document doc = streamToXml(inStream);
		 Gson gson = new Gson();
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		String s =  b.readLine();
		 Log.d("Movie"," reader formed is "+s);
		 
       /*  Movie movies = gson.fromJson(b.readLine(), new TypeToken<Movie>(){}.getType());
         List<MovieDetails> items = new ArrayList<MovieDetails>();
         List<MovieItems> items1 = new ArrayList<MovieItems>();*/
		 List<MovieItems> items1  =  ParseResponse.getMeMovies(s);
		 
       //  items1.addAll(Arrays.asList(movies.GetJSONMoviesByTheaterIDResult.Items));
       /*int   len =   movies.GetJSONMoviesByTheaterIDResult.Items.length;
         for (int i = 0; i < len; i++) {
        	MovieItems m =  movies.GetJSONMoviesByTheaterIDResult.Items[i];
        	MovieDetails mo = new MovieDetails();
        	mo.actor = m.ActorName;
        	mo.genre = m.Genre;
        	mo.
        	items.add(mo);
		}*/
         
		
	//	List<Movie> items = MovieListParser.parseItems(doc);
		return items1;
	}
}