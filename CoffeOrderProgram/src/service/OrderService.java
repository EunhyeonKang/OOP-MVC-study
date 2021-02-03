package service;

import java.util.ArrayList;

import dto.MenuDto;
import dto.OrderDto;

public interface OrderService {
	public ArrayList<MenuDto> getMenu();
	public boolean addBucket(OrderDto dto);				// 장바구니 
	public boolean addOrder(OrderDto dto);
	// 주문 추가
	public void bucketView();	// 장바구니 화면 열기 
	public ArrayList<OrderDto> getAllOrder(String id);	// 모든 주문내역 리스트 가져오기
	public ArrayList<MenuDto> getAllMenu(String id);	// 모든 주문내역 리스트 가져오기
	public boolean addMenu(MenuDto dto);
	public int getMenuCount();
	public int MonthPrice(String date);
	public MenuDto deleteMenu(String name, int price);
}
