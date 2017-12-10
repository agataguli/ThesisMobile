package com.thesis.visageapp.helpers;

import com.thesis.visageapp.objects.User;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RequestResponseHelperTest extends TestCase {
    public void testProcessCreatingUserFromJsonStringWithSuccess() throws Exception {
        // given
        String testPhrase = "test";
        String jsonString = "{\n" +
                "  \"userId\": \"test\",\n" +
                "  \"login\": \"test\",\n" +
                "  \"password\": \"test\",\n" +
                "  \"name\": \"test\",\n" +
                "  \"surname\": \"test\",\n" +
                "  \"email\": \"test\",\n" +
                "  \"phoneNumber\": \"test\",\n" +
                "  \"country\": \"test\",\n" +
                "  \"postCode\": \"test\",\n" +
                "  \"city\": \"test\",\n" +
                "  \"street\": \"test\",\n" +
                "  \"addressDetails\": \"test\",\n" +
                "  \"active\": true,\n" +
                "  \"attributesValues\": [\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    \"test\",\n" +
                "    true\n" +
                "  ]\n" +
                "}";
        // when
        User testUser = RequestResponseStaticPartsHelper.processUserStringJSON(jsonString);

        // then
        Assert.assertEquals(testUser.getLogin(),testPhrase);
        Assert.assertEquals(testUser.getPassword(),testPhrase);
        Assert.assertEquals(testUser.getName(),testPhrase);
        Assert.assertEquals(testUser.getSurname(),testPhrase);
        Assert.assertEquals(testUser.getEmail(),testPhrase);
        Assert.assertEquals(testUser.getPhoneNumber(),testPhrase);
        Assert.assertEquals(testUser.getCountry(),testPhrase);
        Assert.assertEquals(testUser.getPostCode(),testPhrase);
        Assert.assertEquals(testUser.getCity(),testPhrase);
        Assert.assertEquals(testUser.getStreet(),testPhrase);
        Assert.assertEquals(testUser.getAddressDetails(),testPhrase);
        Assert.assertEquals(testUser.isActive(),true);
        // throw no exception
    }
}