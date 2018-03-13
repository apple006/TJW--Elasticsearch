/**
 *    Copyright  2018 tianjunwei
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.tianjunwei;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
import com.alibaba.fastjson.JSON;

/**
 * @author tianjunwei
 * @time 2018下午1:17:31
 */
public class EsSQL {
	
	public static void main(String [] args) throws Exception{
		Properties properties = new Properties();
		properties.put("url", "jdbc:elasticsearch://localhost:9300/");
		DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory
		        .createDataSource(properties);
		dds.setInitialSize(1);
		Connection connection = dds.getConnection();
		String sql2 = "select * FROM index where key='values'";
		PreparedStatement ps = connection.prepareStatement(sql2);
		ResultSet set=ps.executeQuery();

		 ResultSetMetaData data= set.getMetaData();

		 System.out.println( JSON.toJSONString(data));
		 int count= set.getRow();
		 
		 System.out.println(count);
		 System.err.println(data.getColumnCount());
		 while (set.next()) {
			    //sql对应输出
			    System.out.println(set.getString("key") );

			}

		ps.close();
		connection.close();
		dds.close();
	}
}
