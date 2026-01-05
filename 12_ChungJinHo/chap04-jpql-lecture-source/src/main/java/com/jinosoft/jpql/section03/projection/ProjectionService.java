package com.jinosoft.jpql.section03.projection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectionService {

  private final ProjectionRepository repository;

  public ProjectionService(ProjectionRepository repository) {
    this.repository= repository;
  }

	@Transactional
	public List<Menu> singleEntityProjection() {
	  List<Menu> menuList = repository.singleEntityProjection();
	  
		// 엔티티 프로젝션은 영속성 컨텍스트에서 관리하는 객체가 된다.
	  menuList.get(0).setMenuName("세상에서 제일 맛있는 유니콘 고기");
	
	  return menuList;
	}
}