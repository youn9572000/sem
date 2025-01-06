package com.kh.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.product.model.service.ProductService;
import com.kh.product.model.vo.Product;
import com.kh.user.model.service.ReviewService;
import com.kh.user.model.vo.Review;

@WebServlet("/product/detail")
public class ProductDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductDetailServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            String pnoParam = request.getParameter("pno");

           
            if (pnoParam == null || pnoParam.trim().isEmpty()) {
                redirectToErrorPage(request, response, "?곹뭹 踰덊샇媛 ?좏슚?섏? ?딆뒿?덈떎.");
                return;
            }

            int productNo;
            try {
                productNo = Integer.parseInt(pnoParam);
            } catch (NumberFormatException e) {
                redirectToErrorPage(request, response, "?곹뭹 踰덊샇媛 ?レ옄媛 ?꾨떃?덈떎.");
                return;
            }

            // ?곹뭹 ?뺣낫 媛?몄삤湲?
            ProductService productService = new ProductService();
            Product product = productService.getProductById(productNo);

            // ?곹뭹??議댁옱?섏? ?딆쓣 寃쎌슦
            if (product == null) {
                redirectToErrorPage(request, response, "?대떦 ?곹뭹??李얠쓣 ???놁뒿?덈떎.");
                return;
            }

            // 由щ럭 由ъ뒪??媛?몄삤湲?
            ReviewService reviewService = new ReviewService();
            List<Review> reviewList = reviewService.selectReviewList(productNo);

            // JSP濡??곗씠???꾨떖
            request.setAttribute("product", product);
            request.setAttribute("reviewList", reviewList);

            // JSP濡??ъ썙??
            request.getRequestDispatcher("/views/product/product.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            redirectToErrorPage(request, response, "?????녿뒗 ?먮윭媛 諛쒖깮?덉뒿?덈떎.");
        }
    }

    /**
     * ?먮윭 ?섏씠吏濡?由щ떎?대젆?명븯???좏떥由ы떚 硫붿꽌??
     */
    private void redirectToErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMsg)
            throws ServletException, IOException {
        request.setAttribute("errorMsg", errorMsg);
        request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
    }
   

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}