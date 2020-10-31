package org.osrs.api.methods;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RuneLiteAPI {

	public static final String RUNELITE_AUTH = "RUNELITE-AUTH";

	public static final OkHttpClient CLIENT;
	public static final Gson GSON = new Gson();
	public static final MediaType JSON = MediaType.parse("application/json");
	public static String userAgent;

	private static final String BASE = "https://api.runelite.net";
	private static final String WSBASE = "https://api.runelite.net/ws";
	private static final String STATICBASE = "https://static.runelite.net";

	private static final String OPENOSRS_SESSION = "http://session.openosrs.dev";
	private static final String OPENOSRS_XTEA = "http://xtea.openosrs.dev/get";
	private static final String OPENOSRS_ANIMATIONS = "http://animations.openosrs.dev";
	private static final String OPENOSRS_SOUNDS = "http://sounds.openosrs.dev";
	private static final String MAVEN_METADATA = "http://repo.runelite.net/net/runelite/runelite-parent/maven-metadata.xml";

	private static final Properties properties = new Properties();
	private static String version;
	private static String upstreamVersion;
	private static int rsVersion;

	static
	{

		userAgent = "OpenOSRS";
		CLIENT = new OkHttpClient.Builder()
				.pingInterval(30, TimeUnit.SECONDS)
				.addNetworkInterceptor(new Interceptor()
				{
					@Override
					public Response intercept(Chain chain) throws IOException
					{
						Request userAgentRequest = chain.request()
							.newBuilder()
							.header("User-Agent", userAgent)
							.build();
						return chain.proceed(userAgentRequest);
					}
				})
				.build();
		}

	public static HttpUrl getSessionBase()
	{
		return HttpUrl.parse(OPENOSRS_SESSION);
	}

	public static HttpUrl getXteaBase()
	{
		return HttpUrl.parse(OPENOSRS_XTEA);
	}

	public static HttpUrl getAnimationsBase()
	{
		return HttpUrl.parse(OPENOSRS_ANIMATIONS);
	}

	public static HttpUrl getSoundsBase()
	{
		return HttpUrl.parse(OPENOSRS_SOUNDS);
	}

	public static HttpUrl getApiBase()
	{
		final String prop = System.getProperty("runelite.http-service.url");

		if (prop != null && !prop.isEmpty())
		{
			return HttpUrl.parse(prop);
		}

		return HttpUrl.parse(BASE + "/runelite-" + getVersion());
	}

	public static HttpUrl getStaticBase()
	{
		final String prop = System.getProperty("runelite.static.url");

		if (prop != null && !prop.isEmpty())
		{
			return HttpUrl.parse(prop);
		}

		return HttpUrl.parse(STATICBASE);
	}

	public static HttpUrl getWsEndpoint()
	{
		final String prop = System.getProperty("runelite.ws.url");

		if (prop != null && !prop.isEmpty())
		{
			return HttpUrl.parse(prop);
		}

		return HttpUrl.parse(WSBASE);
	}

	public static String getVersion()
	{
		return upstreamVersion;
	}

	public static int getRsVersion()
	{
		return rsVersion;
	}

	public static String getRlpVersion()
	{
		return version;
	}

	private static byte[] downloadUrl(URL toDownload)
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		InputStream stream;
		try
		{
			byte[] chunk = new byte[4096];
			int bytesRead;
			URLConnection conn = toDownload.openConnection();
			conn.setRequestProperty("User-Agent", userAgent);
			stream = conn.getInputStream();

			while ((bytesRead = stream.read(chunk)) > 0)
			{
				outputStream.write(chunk, 0, bytesRead);
			}
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

		return outputStream.toByteArray();
	}

	private static void parseMavenVersion()
	{
		try (ByteArrayInputStream fis = new ByteArrayInputStream(downloadUrl(new URL(MAVEN_METADATA))))
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fis);
			NodeList versionList = doc.getElementsByTagName("version");
			for (int i = 0; i != versionList.getLength(); i++)
			{
				Node node = versionList.item(i);
				if (node.getTextContent() != null)
				{
					upstreamVersion = node.getTextContent();
				}
			}
		}
		catch (Exception ex)
		{
		}
	}
}
