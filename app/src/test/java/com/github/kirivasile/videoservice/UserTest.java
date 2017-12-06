package com.github.kirivasile.videoservice;

import com.github.kirivasile.videoservice.view.User;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    @Test
    public void UserNameTest() {
        User u = new User();

        u.setName("foo");

        Assert.assertEquals("foo", u.getName());
    }
}