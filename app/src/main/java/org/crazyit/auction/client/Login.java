package org.crazyit.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class Login extends Activity
{
	// ��������������ı���
	EditText etName, etPass;
	// ���������������ť
	Button bnLogin, bnCancel;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// ��ȡ�����������༭��
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		// ��ȡ�����е�������ť
		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		// ΪbnCancal��ť�ĵ����¼����¼�������
		bnCancel.setOnClickListener(new HomeListener(this));
		bnLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// ִ������У��
				if (validate())  //��
				{
					// ����¼�ɹ�
					if (loginPro())  //��
					{
						// ����Main Activity
						Intent intent = new Intent(Login.this
							, AuctionClientActivity.class);
						startActivity(intent);
						// �����Activity
						finish();
					}
					else
					{
						DialogUtil.showDialog(Login.this
							, "�û���ƻ�������������������룡", false);
					}
				}
			}
		});
	}

	private boolean loginPro()
	{
		// ��ȡ�û�������û�������
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		JSONObject jsonObj;
		try
		{
			jsonObj = query(username, pwd);
			// ���userId ����0
			if (jsonObj.getInt("userId") > 0)
			{
				return true;
			}
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this
				, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
		}

		return false;
	}

	// ���û�������û����������У��
	private boolean validate()
	{
		String username = etName.getText().toString().trim();
		if (username.equals(""))
		{
			DialogUtil.showDialog(this, "", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		if (pwd.equals(""))
		{
			DialogUtil.showDialog(this, "不能为空", false);
			return false;
		}
		return true;
	}

	// ���巢������ķ���
	private JSONObject query(String username, String password)
		throws Exception
	{
		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "login.jsp";
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}