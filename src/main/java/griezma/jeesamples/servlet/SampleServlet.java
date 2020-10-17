package griezma.jeesamples.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import static java.util.stream.Collectors.*;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;

@Log
@WebServlet("/check")
public class SampleServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet: " + Stream.of(req.getHeaderNames()).collect(toList()));
        try (PrintWriter out = resp.getWriter()) {
            out.println("yes it works");
        }
    }
}
