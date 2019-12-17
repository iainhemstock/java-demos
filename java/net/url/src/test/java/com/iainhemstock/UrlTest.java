/**
 * A URL specifies the location of some resource. Given the following URL:
 *      http://www.example.co.uk:80/search/index.html?manhattan#details
 *
 * it can broken down into the following components:
 *      Protocol: http
 *      Host: www.example.co.uk
 *      Authority: www.example.co.uk:80
 *      Port: 80
 *      Path: /search/index.html
 *      File: /search/index.html?q=manhattan
 *      Query: q=manhattan
 */

package com.iainhemstock;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

public class UrlTest
{
    private URL url;

    @Before
    public void setUp() throws MalformedURLException {
        url  = new URL("http://www.example.co.uk:123/search/index.html?q=manhattan#details");
    }

    @Test
    public void testUrlInstantiation()
    {
        try {
            assertNotNull(new URL("http://www.example.co.uk/index.html"));
            assertNotNull(new URL("http", "www.example.co.uk", "index.html"));
            assertNotNull(new URL("http", "www.example.co.uk", 80, "index.html"));
        }
        catch (MalformedURLException ex) {
            fail();
        }
    }

    /**
     * Returns the protocol section of the url.
     */
    @Test
    public void testProtocol() {
        assertEquals("http", url.getProtocol());
    }

    /**
     * Returns the host section of the url.
     */
    @Test
    public void testHost() {
        assertEquals("www.example.co.uk", url.getHost());
    }

    /**
     * Returns the authority section of the url.
     * The difference between getAuthority() and getHost() is that getAuthority() also includes the port whereas
     * getHost() does not.
     */
    @Test
    public void testAuthority() {
        assertEquals("www.example.co.uk:123", url.getAuthority());
    }

    /**
     * Returns the port section of the url.
     */
    @Test
    public void testPort() {
        assertEquals(123, url.getPort());
    }

    /**
     * Returns the default port for the specified protocol.
     * Default port for HTTP is 80.
     */
    @Test
    public void testDefaultPort() {
        assertEquals(80, url.getDefaultPort());
    }

    /**
     * Returns the path section of the url.
     */
    @Test
    public void testPath() {
        assertEquals("/search/index.html", url.getPath());
    }

    /**
     * Returns the file section of the url.
     */
    @Test
    public void testFile() {
        assertEquals("/search/index.html?q=manhattan", url.getFile());
    }

    /**
     * Returns the query section of the url.
     */
    @Test
    public void testQuery() {
        assertEquals("q=manhattan", url.getQuery());
    }

    /**
     * Returns the reference section of the url.
     */
    @Test
    public void testRef() throws MalformedURLException {
        assertEquals("details", url.getRef());
    }

    /**
     * Returns the user info section of the url.
     */
    @Test
    public void testUserInfo() throws MalformedURLException {
        assertEquals("user:password",
                new URL("http://user:password@example.com").getUserInfo());
    }

    /**
     * As a URL is a subset of a URI then the string URI is the same as the string URL.
     */
    @Test
    public void testToUri() {
        try {
            assertEquals(new URI("http://www.example.co.uk:123/search/index.html?q=manhattan#details"),
                    url.toURI());
        }
        catch (URISyntaxException ex) {
            fail();
        }
    }

    /**
     * Creates an input stream to read from the resource the url points to.
     */
    @Test
    public void testReadHtmlFromUrl() {
        try (InputStream input = new URL("http://www.example.com").openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input)))
        {
            String line = "";
            while ((line = reader.readLine()) != null) {
                // do something
            }
        }
        catch (IOException ex) {
            fail("Couldn't read from input stream");
        }
    }

    @Test
    public void testSaveImageFromUrlToFile() {
        try {
            URL imageUrl = new URL("https://en.wikipedia.org/static/images/project-logos/enwiki.png");
            try {
                BufferedInputStream input = new BufferedInputStream(imageUrl.openStream());
                try {
                    FileOutputStream output = new FileOutputStream("wikipedia-logo.png");
                    try {
                        byte[] bytes = input.readAllBytes();
                        output.write(bytes);
                    }
                    finally { output.close(); }
                }
                finally { input.close(); }
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
                fail();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                fail();
            }
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
            fail();
        }

    }
}
