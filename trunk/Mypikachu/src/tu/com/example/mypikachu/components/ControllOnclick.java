package tu.com.example.mypikachu.components;

import java.util.ArrayList;

import tu.com.example.mypikachu.Menu;
import tu.com.example.mypikachu.Play;

import android.graphics.Point;

public class ControllOnclick {
	// 2 đối tượng để lưu lại vị trí chọn đối tượng pikachu
	public static ItemPikachu item1 = null;
	public static ItemPikachu item2 = null;

	public static boolean isOnClickItem = true;

	/**
	 * Mỗi lần click sẽ lưu lại vị trí đối tượng được click
	 * 
	 * @param item
	 */
	public static void click(ItemPikachu item) {
		if (item1 == null) {
			item1 = item;
		} else {
			item2 = item;
			// Nếu 2 vị trí được chọn thì kiểm tra xem có ăn được không
			checkEat();
		}
	}

	/**
	 * Khởi tạo lại 2 vị trí
	 */
	public static void resetItem() {
		if (item1 != null) {
			item1.setScale(1f);
			item1.setRotation(0);
		}

		if (item2 != null) {
			item2.setScale(1f);
			item2.setRotation(0);
		}

		item1 = null;
		item2 = null;
	}

	/**
	 * Kiểm tra xem có ăn được ko
	 */
	public static void checkEat() {
		ControllOnclick.isOnClickItem = false;
		if (item1.getGT_ITEM() == item2.getGT_ITEM()) {
			if (item1.getI() != item2.getI() || item1.getJ() != item2.getJ()) {

				int i1 = item1.getI(), j1 = item1.getJ(), i2 = item2.getI(), j2 = item2
						.getJ();
				ArrayList<Point> line_point = new ArrayList<Point>();

				if (checkCondition(i1, j1, i2, j2, line_point)) {

					if (line_point.size() < 5) {
						Menu.mSound.playGood();
						// Cộng thêm dollar
						Play.mPlay.mDollar.addDollar(100);

						Play.mPlay.mHint.setVisiable(false);
						// Vẽ đường ăn
						Play.mPlay.mDrawLine
								.addLine(i1, j1, i2, j2, line_point);

						Play.mPlay.removeItem(item1.I, item1.J);
						Play.mPlay.removeItem(item2.I, item2.J);

						MT.mt[item1.I][item1.J] = -1;
						MT.mt[item2.I][item2.J] = -1;

						item1.removeItem();
						item2.removeItem();

						item1 = null;
						item2 = null;

						MT.showMT();

						// Nếu ăn hết thì sẽ hiện thị dialog thông báo
						// Chiến thắng
						if (Play.mPlay.mManagerItemPikachu.list_itemPikachu
								.size() == 0) {
							Play.mPlay.GameOver = true;
							Play.mPlay.showDialogCompleted();
							return;
						}
						// Khi ăn xong 1 cặp thì có thể có hiệu ứng trượt di
						// chuyển các đối tượng khác
						Play.mPlay.mManagerItemPikachu.moveItem(i1, j1, i2, j2);
						MT.showMT();
						return;
					}
				}
			}
		}
		resetItem();
		ControllOnclick.isOnClickItem = true;
		Menu.mSound.playBad();
	}

	/**
	 * Kích hoạt tìm kiếm, nếu không còn quân ăn thì phải đổi chỗ,
	 * 
	 */
	public static void activeSearhHint() {
		while (!ControllOnclick.searchHint()
				&& Play.mPlay.mManagerItemPikachu.list_itemPikachu.size() != 0) {
			Play.mPlay.swapItem();
			MT.showMT();
			Menu.mSound.playRandom();
		}
		ControllOnclick.isOnClickItem = true;
	}

	/**
	 * Kiểm tra xem 2 item này có thỏa mãn các điều kiện hay không
	 */
	public static boolean checkCondition(int i1, int j1, int i2, int j2,
			ArrayList<Point> line_point) {

		if (MT.mt[i1][j1] == -1 || MT.mt[i2][j2] == -1)
			return false;

		// Kiểm tra theo đường thẳng
		line_point.clear();
		boolean check = checkLine(i1, j1, i2, j2, line_point);
		if (check) {
			addIJToListPoint(i1, j1, line_point);
			addIJToListPoint(i2, j2, line_point);
			return true;
		} else

			// Kiểm tra theo chữ L
			line_point.clear();
		if (checkL(i1, j1, i2, j2, line_point)) {

			return true;
		} else

			// Kiểm tra theo chữ U
			line_point.clear();
		if (checkU(i1, j1, i2, j2, true, line_point)) {

			return true;
		} else

			// Kiểm tra theo chữ UL
			line_point.clear();
		if (checkUL(i1, j1, i2, j2, line_point)) {
			if (line_point.size() < 5) {

				return true;
			}
		} else

			// Kiểm tra theo chữ Z
			line_point.clear();
		if (checkZ(i1, j1, i2, j2, line_point)) {

			return true;
		} else

			return false;
	}

	/**
	 * Kiểm tra ăn đường thẳng
	 * 
	 * @param i1
	 * @param j1
	 * @param i2
	 * @param j2
	 * @param line_point
	 *            : Lưu lại các điểm để phục vụ cho việc vẽ đường ăn
	 * @return
	 */
	public static boolean checkLine(int i1, int j1, int i2, int j2,
			ArrayList<Point> line_point) {
		// Xét trường hợp cùng hàng
		if (i1 == i2) {
			int j_start = -1, j_end = -1;
			if (j1 < j2) {
				j_start = j1 + 1;
				j_end = j2;
			}
			if (j1 > j2) {
				j_start = j2 + 1;
				j_end = j1;
			}

			for (int j = j_start; j < j_end; j++) {
				// MyLog.LogInfo("I MT.mt[" +i1+ "]["+j+"] = " + MT.mt[i1][j]);
				if (MT.mt[i1][j] != -1)
					return false;
			}
			return true;
		}
		// Xét trường hợp cùng cột
		else if (j1 == j2) {
			int i_start = -1, i_end = -1;
			if (i1 < i2) {
				i_start = i1 + 1;
				i_end = i2;
			}
			if (i1 > i2) {
				i_start = i2 + 1;
				i_end = i1;
			}
			for (int i = i_start; i < i_end; i++) {
				// MyLog.LogInfo("J MT.mt[" +i+ "]["+j1+"] = " + MT.mt[i][j1]);
				if (MT.mt[i][j1] != -1)
					return false;
			}
			return true;
		}

		return false;
	}

	/**
	 * Kiểm tra xem có ăn được theo chữ L không
	 * 
	 * @param i1
	 * @param j1
	 * @param i2
	 * @param j2
	 * @param line_point
	 * @return
	 */
	public static boolean checkL(int i1, int j1, int i2, int j2,
			ArrayList<Point> line_point) {
		// Lưu lại các tạo độ cần sơ sánh
		Point p1 = new Point();
		Point p2 = new Point();
		// Vị trí 0 là trung tâm: Có 4 góc đánh số từ 1-4 tương ứng
		// 4-----1
		// ---0---
		// 3-----2

		// Góc 1
		if (i1 > i2 && j1 < j2) {
			p1.x = i2;
			p1.y = j1;
			p2.x = i1;
			p2.y = j2;
		}
		// Góc 2
		else if (i1 < i2 && j1 < j2) {
			p1.x = i1;
			p1.y = j2;
			p2.x = i2;
			p2.y = j1;
		}
		// Góc 3
		else if (i1 < i2 && j1 > j2) {
			p1.x = i2;
			p1.y = j1;
			p2.x = i1;
			p2.y = j2;
		}
		// Góc 4
		else if (i1 > i2 && j1 > j2) {
			p1.x = i2;
			p1.y = j1;
			p2.x = i1;
			p2.y = j2;
		}

		if (MT.mt[p1.x][p1.y] == -1) {
			if (checkLine(i1, j1, p1.x, p1.y, line_point)
					&& checkLine(p1.x, p1.y, i2, j2, line_point)) {
				addIJToListPoint(i1, j1, line_point);
				addIJToListPoint(i2, j2, line_point);
				addIJToListPoint(p1.x, p1.y, line_point);
				return true;
			}
		}
		if (MT.mt[p2.x][p2.y] == -1) {
			if (checkLine(i1, j1, p2.x, p2.y, line_point)
					&& checkLine(p2.x, p2.y, i2, j2, line_point)) {
				addIJToListPoint(i1, j1, line_point);
				addIJToListPoint(i2, j2, line_point);
				addIJToListPoint(p2.x, p2.y, line_point);
				return true;
			}
		}

		return false;
	}

	/**
	 * Kiểm tra ăn theo chữ U
	 * 
	 * @param i1
	 * @param j1
	 * @param i2
	 * @param j2
	 * @param checkU
	 * @param line_point
	 * @return
	 */
	public static boolean checkU(int i1, int j1, int i2, int j2,
			boolean checkU, ArrayList<Point> line_point) {
		if (checkU) {
			// Kiểm tra các trường hợp đặc biết. Các vị trí nằm ở biên
			if (i1 == i2 && i1 == 0) {
				addIJToListPoint(i1, j1, line_point);
				addIJToListPoint(i2, j2, line_point);

				addIJToListPoint(i1 - 1, j1, line_point);
				addIJToListPoint(i2 - 1, j2, line_point);
				return true;
			}

			if (i1 == i2 && i1 == MT.row - 1) {
				addIJToListPoint(i1, j1, line_point);
				addIJToListPoint(i2, j2, line_point);

				addIJToListPoint(i1 + 1, j1, line_point);
				addIJToListPoint(i2 + 1, j2, line_point);
				return true;
			}

			if (j1 == j2 && j1 == 0) {
				addIJToListPoint(i1, j1, line_point);
				addIJToListPoint(i2, j2, line_point);

				addIJToListPoint(i1, j1 - 1, line_point);
				addIJToListPoint(i2, j2 - 1, line_point);
				return true;
			}

			if (j1 == j2 && j1 == MT.column - 1) {
				addIJToListPoint(i1, j1, line_point);
				addIJToListPoint(i2, j2, line_point);

				addIJToListPoint(i1, j1 + 1, line_point);
				addIJToListPoint(i2, j2 + 1, line_point);
				return true;
			}
		} else {
			// Kiểm tra các trường hợp đặc biết. Các vị trí nằm ở biên
			if (i1 == i2 && i1 == 0) {
				addIJToListPoint(i1 - 1, j1, line_point);
				addIJToListPoint(i2 - 1, j2, line_point);
				return true;
			}

			if (i1 == i2 && i1 == MT.row - 1) {
				addIJToListPoint(i1 + 1, j1, line_point);
				addIJToListPoint(i2 + 1, j2, line_point);
				return true;
			}

			if (j1 == j2 && j1 == 0) {
				addIJToListPoint(i1, j1 - 1, line_point);
				addIJToListPoint(i2, j2 - 1, line_point);
				return true;
			}

			if (j1 == j2 && j1 == MT.column - 1) {
				addIJToListPoint(i1, j1 + 1, line_point);
				addIJToListPoint(i2, j2 + 1, line_point);
				return true;
			}
		}
		// Xét trường hợp cùng hàng
		if (i1 == i2) {

			for (int i = i1 - 1; i > -1; i--) {
				if (MT.mt[i][j1] == -1 && MT.mt[i][j2] == -1) {
					if (checkLine(i, j1, i, j2, line_point) || i == 0) {

						if (checkU) {
							addIJToListPoint(i1, j1, line_point);
							addIJToListPoint(i2, j2, line_point);
						}
						if (i == 0)
							i = i - 1;
						addIJToListPoint(i, j1, line_point);
						addIJToListPoint(i, j2, line_point);
						return true;
					}
				} else
					break;
			}
			for (int i = i1 + 1; i < MT.row; i++) {

				if (MT.mt[i][j1] == -1 && MT.mt[i][j2] == -1) {
					if (checkLine(i, j1, i, j2, line_point) || i == MT.row - 1) {

						if (checkU) {
							addIJToListPoint(i1, j1, line_point);
							addIJToListPoint(i2, j2, line_point);
						}
						if (i == MT.row - 1)
							i = i + 1;
						addIJToListPoint(i, j1, line_point);
						addIJToListPoint(i, j2, line_point);
						return true;
					}
				} else
					break;
			}
		}
		// Xét trường hợp cùng cột
		else if (j1 == j2) {

			for (int j = j1 - 1; j > -1; j--) {
				if (MT.mt[i1][j] == -1 && MT.mt[i2][j] == -1) {
					if (checkLine(i1, j, i2, j, line_point) || j == 0) {

						if (checkU) {
							addIJToListPoint(i1, j1, line_point);
							addIJToListPoint(i2, j2, line_point);
						}
						if (j == 0)
							j = j - 1;
						addIJToListPoint(i1, j, line_point);
						addIJToListPoint(i2, j, line_point);
						return true;
					}
				} else
					break;
			}
			for (int j = j1 + 1; j < MT.column; j++) {
				if (MT.mt[i1][j] == -1 && MT.mt[i2][j] == -1) {
					if (checkLine(i1, j, i2, j, line_point)
							|| j == MT.column - 1) {

						if (checkU) {
							addIJToListPoint(i1, j1, line_point);
							addIJToListPoint(i2, j2, line_point);
						}
						if (j == MT.column - 1)
							j = j + 1;
						addIJToListPoint(i1, j, line_point);
						addIJToListPoint(i2, j, line_point);
						return true;
					}
				} else
					break;
			}
		}
		return false;
	}

	/**
	 * Kiểm tra ăn dạng U 2 (tạm gọi là U lệch)
	 * 
	 * @param i1
	 * @param j1
	 * @param i2
	 * @param j2
	 * @param line_point
	 * @return
	 */
	public static boolean checkUL(int i1, int j1, int i2, int j2,
			ArrayList<Point> line_point) {
		Point p1 = new Point();
		Point p2 = new Point();
		// Góc 1
		if (i1 > i2 && j1 < j2) {
			p1.x = i2;
			p1.y = j1;
			p2.x = i1;
			p2.y = j2;
		}
		// Góc 2
		else if (i1 < i2 && j1 < j2) {
			p1.x = i1;
			p1.y = j2;
			p2.x = i2;
			p2.y = j1;
		}
		// Góc 3
		else if (i1 < i2 && j1 > j2) {
			p1.x = i2;
			p1.y = j1;
			p2.x = i1;
			p2.y = j2;
		}
		// Góc 4
		else if (i1 > i2 && j1 > j2) {
			p1.x = i2;
			p1.y = j1;
			p2.x = i1;
			p2.y = j2;
		}

		// Kiểm tra p1
		boolean check = false;
		if (MT.mt[p1.x][p1.y] == -1) {
			check = checkU(p1.x, p1.y, i2, j2, false, line_point);
			if (check) {
				check = checkLine(i1, j1, p1.x, p1.y, line_point);
				if (check) {
					addIJToListPoint(i1, j1, line_point);
					addIJToListPoint(i2, j2, line_point);

					return true;
				}
			}
			line_point.clear();
			check = checkU(p1.x, p1.y, i1, j1, false, line_point);
			if (check) {
				check = checkLine(i2, j2, p1.x, p1.y, line_point);
				if (check) {
					addIJToListPoint(i1, j1, line_point);
					addIJToListPoint(i2, j2, line_point);

					return true;
				}
			}
		}

		if (MT.mt[p2.x][p2.y] == -1) {
			line_point.clear();
			check = checkU(p2.x, p2.y, i2, j2, false, line_point);
			if (check) {
				check = checkLine(i1, j1, p2.x, p2.y, line_point);
				if (check) {
					addIJToListPoint(i1, j1, line_point);
					addIJToListPoint(i2, j2, line_point);

					return true;
				}
			}
			line_point.clear();
			check = checkU(p2.x, p2.y, i1, j1, false, line_point);
			if (check) {
				check = checkLine(i2, j2, p2.x, p2.y, line_point);
				if (check) {
					addIJToListPoint(i1, j1, line_point);
					addIJToListPoint(i2, j2, line_point);

					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Kiểm tra ăn theo chữ Z
	 * 
	 * @param i1
	 * @param j1
	 * @param i2
	 * @param j2
	 * @param line_point
	 * @return
	 */
	public static boolean checkZ(int i1, int j1, int i2, int j2,
			ArrayList<Point> line_point) {
		addIJToListPoint(i1, j1, line_point);
		addIJToListPoint(i2, j2, line_point);
		// Xét theo hàng
		if (i1 < i2) {

			int i = i1 + 1;
			while (i < i2) {
				if (MT.mt[i][j1] == -1) {
					if (MT.mt[i][j2] == -1) {
						if (checkLine(i, j2, i2, j2, line_point)
								&& checkLine(i, j2, i, j1, line_point)) {
							addIJToListPoint(i, j2, line_point);
							addIJToListPoint(i, j1, line_point);
							return true;
						}
					}
				} else
					break;
				i++;
			}
		}

		if (i1 > i2) {

			int i = i2 + 1;
			while (i < i1) {
				if (MT.mt[i][j2] == -1) {
					if (MT.mt[i][j1] == -1) {
						if (checkLine(i, j1, i1, j1, line_point)
								&& checkLine(i, j2, i, j1, line_point)) {
							addIJToListPoint(i, j2, line_point);
							addIJToListPoint(i, j1, line_point);
							return true;
						}
					}
				} else
					break;
				i++;
			}
		}

		// Xét theo cột
		if (j1 < j2) {

			int j = j1 + 1;
			while (j < j2) {
				if (MT.mt[i1][j] == -1) {
					if (MT.mt[i2][j] == -1) {
						if (checkLine(i2, j, i2, j2, line_point)
								&& checkLine(i1, j, i2, j, line_point)) {
							addIJToListPoint(i2, j, line_point);
							addIJToListPoint(i1, j, line_point);
							return true;
						}
					}
				} else
					break;
				j++;
			}
		}
		if (j1 > j2) {

			int j = j2 + 1;
			while (j < j1) {
				if (MT.mt[i2][j] == -1) {
					if (MT.mt[i1][j] == -1) {
						if (checkLine(i1, j, i1, j1, line_point)
								&& checkLine(i1, j, i2, j, line_point)) {
							addIJToListPoint(i2, j, line_point);
							addIJToListPoint(i1, j, line_point);
							return true;
						}
					}
				} else
					break;
				j++;
			}
		}
		return false;
	}

	/**
	 * Thêm 1 điểm vào list
	 * 
	 * @param i
	 * @param j
	 * @param line_point
	 */
	public static void addIJToListPoint(int i, int j,
			ArrayList<Point> line_point) {
		Point p = new Point(i, j);
		if (!line_point.contains(p))
			line_point.add(p);
	}

	/**
	 * Tìm kiếm vị trí ăn được
	 * 
	 * @param return_point
	 * @return
	 */
	public static ArrayList<Point> search(ArrayList<Point> return_point) {
		for (int i = 0; i < MT.row; i++) {
			for (int j = 0; j < MT.column; j++) {

				int T1 = MT.mt[i][j];
				if (T1 == -1)
					continue;

				for (int i1 = 0; i1 < MT.row; i1++) {
					for (int j1 = 0; j1 < MT.column; j1++) {
						if (T1 == MT.mt[i1][j1] && (i != i1 || j != j1)) {
							ArrayList<Point> line_point = new ArrayList<Point>();
							if (checkCondition(i, j, i1, j1, line_point)) {
								if (line_point.size() < 5) {

									return_point.add(new Point(i, j));
									return_point.add(new Point(i1, j1));

									return return_point;
								}
							}
						}
					}
				}
			}
		}

		return return_point;
	}

	/**
	 * Tìm kiếm và lưu lại vị trí gợi ý ăn được
	 * 
	 * @return
	 */
	public static boolean searchHint() {
		ArrayList<Point> return_point = new ArrayList<Point>();
		search(return_point);
		if (return_point.size() == 2) {
			Point p1 = return_point.get(0);
			Point p2 = return_point.get(1);
			Play.mPlay.setHint(p1.x, p1.y, p2.x, p2.y);
			return true;
		}

		return false;
	}

}
