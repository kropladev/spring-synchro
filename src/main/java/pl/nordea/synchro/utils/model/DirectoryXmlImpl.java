package pl.nordea.synchro.utils.model;

import java.io.BufferedReader;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class DirectoryXmlImpl implements IDirectoryXmlDao {
    private static final String PREFIX = "directory";
    private static final String SUFFIX = ".xml";
	
	@Autowired
	private LobHandler lobHandler;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insertDirectoryConfig(final MultipartFile file) throws IOException {
		final String xmlValue;

		InputStream is;
		is = file.getInputStream();
		xmlValue = getStringFromInputStream(is);

		jdbcTemplate.update("delete from BPSS_ADMIN.SYNCHRO_CONFIG_DIR");

		jdbcTemplate.update("insert into BPSS_ADMIN.SYNCHRO_CONFIG_DIR(scd_id,scd_file_name,scd_change,scd_file) values (?, ?, ?, ?)"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, 1);
						ps.setString(2, file.getOriginalFilename());
						ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
						lobHandler.getLobCreator().setClobAsString(ps, 4,xmlValue);
					}
				});
		jdbcTemplate
		.update("update BPSS_ADMIN.SYNCHRO_CONFIG set sc_value=? WHERE sc_name=? and sc_param=?", file.getOriginalFilename(),"dir.directoryFile","SYNCHRO");
	}

	@Override
	public String getDirectoryConfig() throws IOException, URISyntaxException {
		//OutputStream outputStream=null;
		InputStream inputStream=null;
		 @SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList list= (ArrayList) jdbcTemplate.query("select scd_file from BPSS_ADMIN.SYNCHRO_CONFIG_DIR where scd_id=?", new Object[] { 1 }, new RowMapper() {
		      public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		          String clobText =lobHandler.getClobAsString(rs, 1);
		    	  return clobText;
		      }
		  });
		//transform data to inputStream
		inputStream = new ByteArrayInputStream( ((String)list.get(0)).getBytes());
		 File dirTmpFile=stream2file(inputStream);
		 inputStream.close();
		return dirTmpFile.getPath();
	}

    private static File stream2file (InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        	FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(in, out);
            out.close();
        return tempFile;
    }
	
	
	/**
	 * convert InputStream into String value
	 * @param is
	 * @return
	 */
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

}
