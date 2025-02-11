package com.techelevator.controller;

import com.techelevator.dao.DanceHallDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.DanceHall;
import com.techelevator.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(path = "/dancehalls")

public class DanceHallController {

    private DanceHallDao danceHallDao;
    private UserDao userDao;

    public DanceHallController(DanceHallDao danceHallDao, UserDao userDao){
        this.danceHallDao = danceHallDao;
        this.userDao = userDao;
    }

    //create and add a new dance hall - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public DanceHall add(@Valid @RequestBody DanceHall danceHall, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userDao.getUserByUsername(username);

            if (user != null) {
                danceHall.setUserId(user.getId());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }
        if (danceHall.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must be provided");
        }
        return danceHallDao.createDanceHall(danceHall);
    }

    //retrieve a list of all dance halls - everyone
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<DanceHall> list() {
        return danceHallDao.getDanceHalls();
    }

    //retrieve information on an existing dance hall by dance hall id - everyone
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public DanceHall get(@PathVariable int id) {
        DanceHall danceHall = danceHallDao.getDanceHallById(id);
        if (danceHall == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dance Hall not found");
        }
        else {
            return danceHall;
        }
    }

    //update an existing dance hall by dance hall id - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public DanceHall update(@Valid @RequestBody DanceHall danceHall, @PathVariable int id) {

        //check that the dance hall exists
        DanceHall existingDanceHall = danceHallDao.getDanceHallById(id);
        if (existingDanceHall == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Dance hall not found");
        }

        // The id on the path takes precedence over the id in the request body, if any
        danceHall.setDanceHallId(id);
        try {
            DanceHall updatedDanceHall = danceHallDao.updateDanceHall(danceHall);
            return updatedDanceHall;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dance Hall not found");
        }
    }

    //delete an existing dance hall by dance hall id - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        if (danceHallDao.getDanceHallById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Dance hall not found");
        }
        danceHallDao.deleteDanceHallById(id);
    }

}
