package com.relateddigital.euromessage;

public class TestPush {

    public static String testImage = "{\n" +
            "    \"pushType\": \"Image\",\n" +
            "    \"url\": \"https://www.euromsg.com/\",\n" +
            "    \"mediaUrl\": \"https://mcdn01.gittigidiyor.net/ps/banner_1583388342.jpg\",\n" +
            "    \"pushId\": \"bea5303f-11aa-4ac7-aae8-2265ba63b535\",\n" +
            "    \"altUrl\": \"\",\n" +
            "    \"sound\": \"default\",\n" +
            "    \"message\": \"Bugünü kaçırma\",\n" +
            "    \"title\": \"BUGÜNE ÖZEL 150 TL İNDİRİM\"\n" +
            "\n" +
            "}";;


    public static String testText = "{\n" +
            "    \"pushType\": \"Text\",\n" +
            "    \"url\": \"https://www.euromsg.com/\",\n" +
            "    \"mediaUrl\": \"\",\n" +
            "    \"pushId\": \"bea5303f-11aa-4ac7-aae8-2265ba63b535\",\n" +
            "    \"altUrl\": \"\",\n" +
            "    \"sound\": \"default\",\n" +
            "    \"message\": \"Buzdolabı, Soğutucu Almanın Tam Sırası\",\n" +
            "    \"title\": \"BUGÜNE ÖZEL 150 TL İNDİRİM\"\n" +
            "\n" +
            "}";
    public static String testCarousel = "{\n" +
            "    \"pushType\": \"Text\",\n" +
            "    \"url\": \"https://www.euromsg.com/\",\n" +
            "    \"mediaUrl\": \"\",\n" +
            "    \"pushId\": \"bea5303f-11aa-4ac7-aae8-2265ba63b535\",\n" +
            "    \"altUrl\": \"\",\n" +
            "    \"sound\": \"default\",\n" +
            "    \"message\": \"Akıllı saatlerde göz alıcı kampanya\",\n" +
            "    \"title\": \"İndirim Alarmı\",\n" +
            "    \"elements\": [{\n" +
            "            \"id\": 1,\n" +
            "            \"title\": \"Süper İndirim\",\n" +
            "            \"content\": \"Akıllı saatlerde akılalmaz indirimleri kaçırmayın. Harika bir saat\",\n" +
            "            \"url\": \"https://www.euromsg.com/\",\n" +
            "            \"picture\": \"https://mcdn01.gittigidiyor.net/52578/tn50/525782224_tn50_0.jpg?1583398\"\n" +
            "        }, {\n" +
            "            \"id\": 2,\n" +
            "            \"title\": \"Mükemmel İndirim\",\n" +
            "            \"content\": \"Hayatını akıllı saatlerle beraber çok kolaylaştırabilirsin. Akıllı saatleri incele!\",\n" +
            "            \"url\": \"https://www.euromsg.com/\",\n" +
            "            \"picture\": \"https://mcdn01.gittigidiyor.net/49668/tn50/496687214_tn50_0.jpg?1583398\"\n" +
            "        }, {\n" +
            "            \"id\": 3,\n" +
            "            \"title\": \"Şaşırtıcı İndirim\",\n" +
            "            \"content\": \"Akıllı Saatler ! \",\n" +
            "            \"url\": \"https://www.euromsg.com/\",\n" +
            "            \"picture\": \"https://mcdn01.gittigidiyor.net/49101/tn50/491019547_tn50_0.jpg?1583398\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";



    public static String testAction = "{\n" +
            "    \"pushType\": \"Action\",\n" +
            "    \"url\": \"http://www.google.com.tr\",\n" +
            "    \"mediaUrl\": \"\",\n" +
            "    \"pushId\": \"bea5303f-11aa-4ac7-aae8-2265ba63b535\",\n" +
            "    \"altUrl\": \"\",\n" +
            "    \"sound\": \"default\",\n" +
            "    \"message\": \"Sepetinizde ürünler var. Haydi siparişi tamamla..\",\n" +
            "    \"title\": \"Bil bakalım. Ne farkettik! :) \",\n" +
            "    \"actionElements\": [{\n" +
            "            \"id\": \"1\",\n" +
            "            \"buttonTitle\": \"Tamamla\"\n" +
            "        }, {\n" +
            "             \"id\": \"2\",\n" +
            "            \"buttonTitle\": \"Ürünlere Bak\"\n" +
            "        }, {\n" +
        "             \"id\": \"3\",\n" +
                "            \"buttonTitle\": \"Sonra\"\n" +
                "        }\n" +
            "    ]\n" +
            "}";

}
