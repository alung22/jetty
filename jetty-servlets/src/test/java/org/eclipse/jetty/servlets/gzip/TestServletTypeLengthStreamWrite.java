package org.eclipse.jetty.servlets.gzip;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlets.GzipFilter;

/**
 * A sample servlet to serve static content, using a order of construction that has caused problems for
 * {@link GzipFilter} in the past.
 * 
 * Using a real-world pattern of:
 * 
 * <pre>
 *  1) set content type
 *  2) set content length
 *  3) get stream
 *  4) write
 * </pre>
 * 
 * @see <a href="Eclipse Bug 354014">http://bugs.eclipse.org/354014</a>
 */
@SuppressWarnings("serial")
public class TestServletTypeLengthStreamWrite extends TestDirContentServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String fileName = request.getServletPath();
        byte[] dataBytes = loadContentFileBytes(fileName);

        if (fileName.endsWith("txt"))
            response.setContentType("text/plain");
        else if (fileName.endsWith("mp3"))
            response.setContentType("audio/mpeg");

        response.setContentLength(dataBytes.length);

        ServletOutputStream out = response.getOutputStream();
        out.write(dataBytes);
    }
}