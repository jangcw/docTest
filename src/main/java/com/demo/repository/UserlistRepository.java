package com.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.dto.Userlist;

@Repository
public interface UserlistRepository extends JpaRepository<Userlist, String>, JpaSpecificationExecutor<Userlist> {
	
	 @Query("SELECT m FROM Userlist m WHERE m.uname = :uname")
	 List<Userlist> findByUname(@Param("uname") String uname);
	 
	 @Query("""
			    SELECT m
			    FROM Userlist m
			    WHERE (:uname IS NULL OR m.uname LIKE CONCAT('%', :uname, '%'))
			      AND (:udept IS NULL OR m.udept LIKE CONCAT('%', :udept, '%'))
			      AND (:wcode IS NULL OR m.wcode LIKE CONCAT('%', :wcode, '%'))
			""")
			List<Userlist> searchUsers(
			        @Param("uname") String uname,
			        @Param("udept") String udept,
			        @Param("wcode") String wcode
			);
	 
	 @Query("""
			    SELECT m
			    FROM Userlist m
			    WHERE (:uname IS NULL OR m.uname LIKE CONCAT('%', :uname, '%'))
			      AND (:udept IS NULL OR m.udept LIKE CONCAT('%', :udept, '%'))
			      AND (:wcode IS NULL OR m.wcode LIKE CONCAT('%', :wcode, '%'))
			""")
			Page<Userlist> searchUsers(
			        @Param("uname") String uname,
			        @Param("udept") String udept,
			        @Param("wcode") String wcode,
			        Pageable pageable
			);
	 
	 @Query(value = """
			    WITH RECURSIVE cte AS (
			        SELECT
			            menu_id,
			            COALESCE(up_menu_id, 0) AS up_menu_id,
			            menu_nm,
			            sort,
			            sort::text AS sort_path,
			            1 AS level
			        FROM t_menu
			        WHERE homepage = :homepage
			          AND use_yn = 'Y'
			          AND up_menu_id IS NULL

			        UNION ALL

			        SELECT
			            p.menu_id,
			            COALESCE(p.up_menu_id, 0) AS up_menu_id,
			            p.menu_nm,
			            p.sort,
			            cte.sort_path || p.sort::text AS sort_path,
			            cte.level + 1 AS level
			        FROM t_menu p
			        INNER JOIN cte
			            ON cte.menu_id = COALESCE(p.up_menu_id, 0)
			        WHERE p.homepage = :homepage
			          AND p.use_yn = 'Y'
			          AND p.gnb_use_yn = 'Y'
			    )
			    SELECT
			        a.menu_id,
			        COALESCE(a.up_menu_id, a.menu_id) AS up_menu_id,
			        (
			            SELECT COUNT(menu_id)
			            FROM t_menu
			            WHERE up_menu_id = a.menu_id
			        ) AS sub_cnt,
			        b.menu_nm,
			        a.sort,
			        b.homepage,
			        b.menu_desc,
			        b.image,
			        (
			            SELECT origin_file_nm
			            FROM t_commonfile
			            WHERE file_id = b.image
			        ) AS image_nm,
			        b.menu_type,
			        b.ref_menu,
			        (
			            SELECT
			                CASE
			                    WHEN m.menu_type = 'F' THEN m.url
			                    WHEN m.menu_type = 'B'
			                        THEN '/front/board/boardContentsListPage.do?board_id=' || m.board
			                    WHEN m.menu_type = 'C'
			                        THEN '/front/intropage/intropageShow.do?page_id=' || m.contents
			                    ELSE m.url
			                END
			            FROM t_menu m
			            WHERE m.menu_id = b.ref_menu
			        ) AS ref_menu_url,
			        b.contents,
			        b.board,
			        b.url,
			        b.gnb_use_yn,
			        b.footer_use_yn,
			        b.sitemap_use_yn,
			        b.https_support_yn,
			        b.target_set,
			        b.width, 
			        b.height,
			        b.top,
			        b."left",
			        b.charge_dept,
			        b.charge_telno,
			        b.charge_email,
			        b.nuri,
			        b.menu_keyword

			    FROM cte AS a
			    INNER JOIN t_menu AS b
			        ON a.menu_id = b.menu_id
			    ORDER BY a.sort_path
			    """, nativeQuery = true)
			List<Userlist> findUsers(@Param("homepage") String homepage);
}