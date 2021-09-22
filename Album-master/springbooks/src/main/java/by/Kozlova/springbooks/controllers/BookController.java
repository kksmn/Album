
package by.Kozlova.springbooks.controllers;

import by.Kozlova.springbooks.Models.Song;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Controller
public class BookController {

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = { "/songlist"}, method = RequestMethod.GET)
    public String songlist(Model model) {
        model.addAttribute("songs",Song.songs);
        return "songslist";
    }
    @RequestMapping(value = {"addsong"}, method = RequestMethod.GET)
    public String addSong(Model model) {
        log.info("/song was added");
        return "addsong";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public String add(Model model) {
        return "addsong";
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
    public String editSong(@PathVariable(value="id") int id, Model model) {
        Song editsong = new Song();
        for (var x : Song.songs) {
            if (x.getId() == id) {
                editsong = x;
                break;
            }
        }
            model.addAttribute("song", editsong);
            return "editsong";
        }
    @RequestMapping(value = {"/editsong/{id}"}, method = RequestMethod.POST)
    public String editSong(@PathVariable(value="id") int id,@RequestParam String name,@RequestParam String author, Model model)
    {

        if(name != null && !name.trim().isEmpty() && author != null && !author.trim().isEmpty()) {
            for(var x:Song.songs) {
                if(x.getId()==id) {
                    x.setAuthor(author);
                    x.setName(name);
                }
            }

        }
        model.addAttribute("songs",Song.songs);
        log.info("/song was edited");
        return "redirect:/songlist";
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public String deleteSong(@PathVariable(value="id") int id,Model model) {
        for(var x:Song.songs){
            if(x.getId()==id) {
                Song.songs.remove(x);
                break;
            }
        }
        model.addAttribute("songs",Song.songs);
        log.info("/song was deleted");
        return "redirect:/songlist";
    }
    //songslist
    @RequestMapping(value = {"list"}, method = RequestMethod.GET)
    public String songList(Model model) {
        return "songslist";
    }

    @RequestMapping(value = {"addsong"}, method = RequestMethod.POST)
    public String saveSong(@RequestParam String name,@RequestParam String author, Model model)
    {
        int id=0;
        boolean flag;
        if (name.length() > 0  && author.length() > 0) {
            do{
                flag=false;
                id=Song.setId();
                for(var x:Song.songs) {
                    if (x.getId() == id)
                        flag = true;
                }
            }while(flag);
                Song newSong = new Song(name, author, id);
                Song.songs.add(newSong);

        }
        model.addAttribute("songs",Song.songs);

        return "songslist";
    }

    @RequestMapping(value = {"deletesong"}, method = RequestMethod.POST)
    public String deleteSong(@RequestParam String name,@RequestParam String author, Model model)
    {

        int count=Song.songs.size();
        if (name != null && name.length() > 0
                && author != null && author.length() > 0 && Song.songs.size()!=0) {

            for(int i=0; i<count;i++)
            {
                if(Song.songs.get(i).getName().equals(name) && Song.songs.get(i).getAuthor().equals(author))
                    Song.songs.remove(Song.songs.get(i));

            }
        }
        model.addAttribute("songs",Song.songs);
        return "songslist";
    }



}




