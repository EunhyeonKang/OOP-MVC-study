package service.impl;
import java.util.ArrayList;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import dto.MenuDto;
import dto.OrderDto;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
	OrderDao dao = new OrderDaoImpl();
	
	@Override
	public ArrayList<MenuDto> getMenu() {
		return dao.getMenu();
	}

	@Override
	public boolean addOrder(OrderDto dto) {
		return dao.addOrder(dto);
	}

	@Override
	public boolean addBucket(OrderDto dto) {
		return dao.addBucket(dto);
	}

	@Override
	public void bucketView() {
		dao.bucketView();
	}


	@Override
	public ArrayList<OrderDto> getAllOrder(String id) {
		return dao.getAllOrder(id);
	}
	
	@Override
	public ArrayList<MenuDto> getAllMenu(String id) {
		return dao.getAllMenu(id);
	}
	@Override
	public boolean addMenu(MenuDto dto) {
		return dao.addMenu(dto);
	}
	
	@Override
	public MenuDto deleteMenu(String name, int price) {
		return dao.deleteMenu(name,price);
	}
	@Override
	public int getMenuCount() {
		return dao.getMenuCount();
	}
	
	@Override
	public int MonthPrice(String date) {
		return dao.MonthPrice(date);
	}
}
