package vip.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import utils.PathUtil;
import utils.ResponseCode;
import utils.ResultData;
import vip.service.ShareCodeService;

@Service
public class ShareCodeServiceImpl implements ShareCodeService {
	private Logger logger = LoggerFactory.getLogger(ShareCodeServiceImpl.class);

	private final static String TEMPLATE_FG_PATH = "/material/image/sharecode.png";
	
	@Override
	public ResultData customizeShareCode(String path, String value) {
		ResultData result = new ResultData();
		try {
			StringBuffer pic = new StringBuffer(PathUtil.retrivePath()).append(path);
			StringBuffer fg = new StringBuffer(PathUtil.retrivePath()).append(TEMPLATE_FG_PATH);
			BufferedImage big = ImageIO.read(new File(pic.toString()));
			BufferedImage small = ImageIO.read(new File(fg.toString()));
			Graphics2D g = big.createGraphics();
			g.drawImage(small, big.getWidth() - small.getWidth(), big.getHeight() - small.getHeight(), small.getWidth(), small.getHeight(), null);
			Font font = new Font("arial", Font.PLAIN, 50);
			g.setColor(Color.BLUE);
			g.setFont(font);
			g.drawString(value, big.getWidth() - small.getWidth() + 160, big.getHeight() - small.getHeight() + 195);
			g.dispose();
			ImageIO.write(big, "png", new File(pic.toString()));
			result.setData(path);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResponseCode(ResponseCode.RESPONSE_ERROR);
			result.setDescription(e.getMessage());
		}
		return result;
	}

}
