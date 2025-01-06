package com.kh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.user.model.service.ReviewService;
import com.kh.user.model.service.SaveListService;
import com.kh.user.model.vo.Member;
import com.kh.user.model.vo.Save;

/**
 * Servlet implementation class SaveDelete
 */
@WebServlet("/kart/delete")
public class SaveDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SaveDelete() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/kart/savelist");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        // 로그인 확인
        if (loginUser == null) {
            session.setAttribute("alertMsg", "로그인이 필요합니다.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // 요청 파라미터에서 productNo 가져오기
            String productNoStr = request.getParameter("productNo");
            boolean isDeleteAll = "true".equals(request.getParameter("deleteAll")); // 전체 삭제 여부 확인

            SaveListService saveListService = new SaveListService();

            if (isDeleteAll) {
                // 전체 삭제 처리
                int result = saveListService.deleteAllSaveList(loginUser.getUserNo());

                if (result > 0) {
                    session.setAttribute("alertMsg", "찜 목록이 모두 삭제되었습니다.");
                } else {
                    session.setAttribute("alertMsg", "찜 목록 전체 삭제에 실패했습니다. 다시 시도해주세요.");
                }
            } else {
                // 특정 상품 삭제 처리
                if (productNoStr == null || productNoStr.trim().isEmpty()) {
                    session.setAttribute("alertMsg", "유효하지 않은 요청입니다. 상품 번호가 필요합니다.");
                    response.sendRedirect(request.getContextPath() + "/kart/savelist");
                    return;
                }

                int productNo = Integer.parseInt(productNoStr);

                // Save 객체 생성
                Save saveToDelete = Save.builder()
                        .userId(loginUser.getUserNo())
                        .productNo(productNo)
                        .build();

                // 찜 목록에서 삭제
                int result = saveListService.deleteSaveList(saveToDelete);

                // 결과 처리
                if (result > 0) {
                    session.setAttribute("alertMsg", "상품이 찜 목록에서 삭제되었습니다.");
                } else {
                    session.setAttribute("alertMsg", "찜 목록 삭제에 실패했습니다. 다시 시도해주세요.");
                }
            }

            // 찜 목록 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/kart/savelist");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            session.setAttribute("alertMsg", "유효하지 않은 상품 번호 형식입니다.");
            response.sendRedirect(request.getContextPath() + "/kart/savelist");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "서버 오류가 발생했습니다.");
            request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
        }
    }
}
