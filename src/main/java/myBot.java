import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.objects.Update;


import java.io.*;

import java.net.URL;
import java.net.URLConnection;



public class myBot extends TelegramLongPollingBot {

    public  void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        System.out.println(message);
        if (message.startsWith("/курс")) {
            String arg = new String(message);
            arg = arg.substring(6);
            sendMsg(update.getMessage().getChatId().toString(),getCurrencyValue(arg));
        } else {
            sendMsg(update.getMessage().getChatId().toString(), message);
        }
    }
    public synchronized void sendMsg(String chartId, String message){
        SendMessage  sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chartId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "MyFirstJavaBotIsAlreadyTakenBot";
    }

    @Override
    public String getBotToken() {
        return "1163152556:AAHFBnbrunigQ_UWe5sk9OG-QtWePuftWYw";
    }
    public String getCurrencyValue(String arg) {
        StringBuffer inputLine = new StringBuffer();
        String fileURLString = "https://www.cbr.ru/scripts/XML_daily_eng.asp";
        try {
            URL XMLFileURL = new URL(fileURLString);
            URLConnection connection = XMLFileURL.openConnection();
            connection.addRequestProperty("Content-Type", "application/xml; charset=utf-8");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String tmp;

            while ((tmp = in.readLine()) != null){
                inputLine.append(tmp);
            }
            System.out.println(inputLine);
            switch (arg){
                case ("доллара"):
                    tmp = inputLine.substring(inputLine.indexOf("<Valute ID=\"R01235\">"),inputLine.indexOf("</Valute>",inputLine.indexOf("<Valute ID=\"R01235\">")));
                    tmp = tmp.substring(tmp.indexOf("<Value>")+ 7,tmp.indexOf("</Value>"));
                    return new String("Текущий курс: " + tmp);

                case ("евро"):
                    tmp = inputLine.substring(inputLine.indexOf("<Valute ID=\"R01239\">"),inputLine.indexOf("</Valute>",inputLine.indexOf("<Valute ID=\"R01239\">")));
                    tmp = tmp.substring(tmp.indexOf("<Value>")+ 7,tmp.indexOf("</Value>"));
                    return new String("Текущий курс: " + tmp);
                default:
                    return "Мне было лень добавлять эту валюту.";

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new String(inputLine);
    }
}
