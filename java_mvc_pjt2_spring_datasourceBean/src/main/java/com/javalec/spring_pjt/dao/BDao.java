package com.javalec.spring_pjt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.javalec.spring_pjt.dto.BDto;
import com.javalec.spring_pjt_board.util.Constant;

public class BDao {
	
//	DataSource dataSource;
		   
	JdbcTemplate template = null;
	
	public BDao() {
		
		template = Constant.template;
		
//		try {
//			Context context = new InitialContext();
//			dataSource = (javax.sql.DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	
	public ArrayList<BDto> list() {
		
		String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
		return (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
//		ArrayList<BDto> dtos = new ArrayList<BDto>();
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
//			preparedStatement = connection.prepareStatement(query);
//			resultSet = preparedStatement.executeQuery();
//			
//			while(resultSet.next()) {
//				int bId = resultSet.getInt("bId");
//				String bName = resultSet.getString("bName");
//				String bTitle = resultSet.getString("bTitle");
//				String bContent = resultSet.getString("bContent");
//				Timestamp bDate = resultSet.getTimestamp("bDate");
//				int bHit = resultSet.getInt("bHit");
//				int bGroup = resultSet.getInt("bGroup");
//				int bStep = resultSet.getInt("bStep");
//				int bIndent = resultSet.getInt("bIndent");
//				
//				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//				
//				dtos.add(dto);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		return dtos;
	} // end if list()
	
	public void write(final String bName,final String bTitle,final String bContent) {
		
		final String query = "insert into mvc_board ( bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent ) values (mvc_board_seq.nextval,?,?,?,0,mvc_board_seq.currval,0,0)";
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				
				return pstmt;
			}
		});
		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "insert into mvc_board ( bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent ) values (mvc_board_seq.nextval,?,?,?,0,mvc_board_seq.currval,0,0)";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setString(1, bName);
//			preparedStatement.setString(2, bTitle);
//			preparedStatement.setString(3, bContent);
//			
//			preparedStatement.executeUpdate();
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//			
//		}
		
	}  //end of write()
	
	public BDto contentView(String strbId) {
				
		upHit(strbId);  // 조회수 증가
		
		String query = "select * from mvc_board where bId = " + strbId;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
//		BDto dto = null;
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "select * from mvc_board where bId = ? ";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(strbId));
//			resultSet = preparedStatement.executeQuery();
//			
//			if(resultSet.next()) {
//				int bId = resultSet.getInt("bId");
//				String bName = resultSet.getString("bName");
//				String bTitle = resultSet.getString("bTitle");
//				String bContent = resultSet.getString("bContent");
//				Timestamp bDate = resultSet.getTimestamp("bDate");
//				int bHit = resultSet.getInt("bhit");
//				int bGroup = resultSet.getInt("bGroup");
//				int bStep = resultSet.getInt("bStep");
//				int bIndent = resultSet.getInt("bIndent");
//				
//				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//			}		
//		} catch (Exception e) {
//			e.printStackTrace();
//		} try {
//			
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		return dto;
		
	} // end of contentView()
	
	public void modify(final String bId,final String bName,final String bTitle,final String bContent) {
		
		String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bId));
			}
		});
		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setString(1, bName);
//			preparedStatement.setString(2, bTitle);
//			preparedStatement.setString(3, bContent);
//			preparedStatement.setInt(4, Integer.parseInt(bId));
//			
//			preparedStatement.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(preparedStatement != null ) preparedStatement.close();
//				if(connection != null ) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
		
		
	} //end of modify
	
	public void delete(final String bId) {
		
		String query = "delete from mvc_board where bId = ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(bId));				
			}
		});

		downSeq(bId); // seq 정리
		
		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "delete from mvc_board where bId = ?";			
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(bId));
//			preparedStatement.executeUpdate();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null ) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}		
	} //end of delete()
	
	public BDto replyView(String strbId) {
		
		String query = "select * from mvc_board where bId = " + strbId;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
//		BDto dto = null;
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "select * from mvc_board where bId = ? ";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(strbId));
//			resultSet = preparedStatement.executeQuery();
//			
//			if(resultSet.next()) {
//				int bId = resultSet.getInt("bId");
//				String bName = resultSet.getString("bName");
//				String bTitle = resultSet.getString("bTitle");
//				String bContent = resultSet.getString("bContent");
//				Timestamp bDate = resultSet.getTimestamp("bDate");
//				int bHit = resultSet.getInt("bhit");
//				int bGroup = resultSet.getInt("bGroup");
//				int bStep = resultSet.getInt("bStep");
//				int bIndent = resultSet.getInt("bIndent");
//				
//				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//			}		
//		} catch (Exception e) {
//			e.printStackTrace();
//		} try {
//			
//		} finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		return dto;
	} // end of reply_view()
	
	public void reply(final String bId,final String bName,final String bTitle,final String bContent,final String bGroup,final String bStep,final String bIndent) {
		
		replyshape(bGroup, bStep);  // 들여쓰기
		String query ="insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval,?,?,?,?,?,?)";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bGroup));
				ps.setInt(5, Integer.parseInt(bStep)+1);
				ps.setInt(6, Integer.parseInt(bIndent)+1);
			}
		});
		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {			
//			connection = dataSource.getConnection();
//			String query ="insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval,?,?,?,?,?,?)";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setString(1, bName);
//			preparedStatement.setString(2, bTitle);
//			preparedStatement.setString(3, bContent);
//			preparedStatement.setInt(4, Integer.parseInt(bGroup));
//			preparedStatement.setInt(5, Integer.parseInt(bStep)+1);
//			preparedStatement.setInt(6, Integer.parseInt(bIndent)+1);
//			
//			preparedStatement.executeUpdate();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
				
	} //end of reply()
	
	public void upHit(final String bId) {
		
		String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(bId));				
			}
		});
		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(bId));
//			
//			preparedStatement.executeUpdate();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
		
	} //end of upHit()
	
public void replyshape(final String strbGroup, final String strbStep) {
	
	String query = "update mvc_board set bStep = bStep+1 where bGroup = ? and bStep > ?";
	template.update(query, new PreparedStatementSetter() {
		
		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, Integer.parseInt(strbGroup));
			ps.setInt(2, Integer.parseInt(strbStep));
		}
	});
		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "update mvc_board set bStep = bStep+1 where bGroup = ? and bStep > ?";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(strbGroup));
//			preparedStatement.setInt(2, Integer.parseInt(strbStep));
//			
//			preparedStatement.executeUpdate();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
		
	} //end of upHit()
	
	public void downSeq(final String bId) {
		
		String query = "update mvc_board set bId = bId-1 where bId > ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(bId));				
			}
		});
		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "update mvc_board set bId = bId-1 where bId > ?";
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, Integer.parseInt(bId));
//			
//			preparedStatement.executeUpdate();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
		
	} // end of downSeq()

}
