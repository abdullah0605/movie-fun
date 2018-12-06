package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    MoviesBean moviesBean;

    public HomeController() {}

    @Autowired
     public HomeController(MoviesBean movieBean) {
        this.moviesBean = movieBean;

    }

   @GetMapping("/setup")
    public String setup(Map<String, Object> movieMap) {
       moviesBean.addMovie(new Movie("Wedding Crashers", "David Dobkin", "Comedy", 7, 2005));
       moviesBean.addMovie(new Movie("Starsky & Hutch", "Todd Phillips", "Action", 6, 2004));
       moviesBean.addMovie(new Movie("Shanghai Knights", "David Dobkin", "Action", 6, 2003));
      movieMap.put("movies", moviesBean.getMovies());
       return "setup";
   }

    @GetMapping("/")
    public String index()   {

        return "index";
    }
}
