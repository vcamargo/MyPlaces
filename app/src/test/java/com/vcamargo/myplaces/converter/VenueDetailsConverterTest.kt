package com.vcamargo.myplaces.converter

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test

class VenueDetailsConverterTest {
    companion object {
        const val API_RESPONSE = "{\n" +
                "  \"meta\": {\n" +
                "    \"code\": 200,\n" +
                "    \"requestId\": \"59a45921351e3d43b07028b5\"\n" +
                "  },\n" +
                "  \"response\": {\n" +
                "    \"venue\": {\n" +
                "      \"id\": \"412d2800f964a520df0c1fe3\",\n" +
                "      \"name\": \"Central Park\",\n" +
                "      \"contact\": {\n" +
                "        \"phone\": \"2123106600\",\n" +
                "        \"formattedPhone\": \"(212) 310-6600\",\n" +
                "        \"twitter\": \"centralparknyc\",\n" +
                "        \"instagram\": \"centralparknyc\",\n" +
                "        \"facebook\": \"37965424481\",\n" +
                "        \"facebookUsername\": \"centralparknyc\",\n" +
                "        \"facebookName\": \"Central Park\"\n" +
                "      },\n" +
                "      \"location\": {\n" +
                "        \"address\": \"59th St to 110th St\",\n" +
                "        \"crossStreet\": \"5th Ave to Central Park West\",\n" +
                "        \"lat\": 40.78408342593807,\n" +
                "        \"lng\": -73.96485328674316,\n" +
                "        \"postalCode\": \"10028\",\n" +
                "        \"cc\": \"US\",\n" +
                "        \"city\": \"New York\",\n" +
                "        \"state\": \"NY\",\n" +
                "        \"country\": \"United States\",\n" +
                "        \"formattedAddress\": [\n" +
                "          \"59th St to 110th St (5th Ave to Central Park West)\",\n" +
                "          \"New York, NY 10028\",\n" +
                "          \"United States\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"canonicalUrl\": \"https://foursquare.com/v/central-park/412d2800f964a520df0c1fe3\",\n" +
                "      \"categories\": [\n" +
                "        {\n" +
                "          \"id\": \"4bf58dd8d48988d163941735\",\n" +
                "          \"name\": \"Park\",\n" +
                "          \"pluralName\": \"Parks\",\n" +
                "          \"shortName\": \"Park\",\n" +
                "          \"icon\": {\n" +
                "            \"prefix\": \"https://ss3.4sqi.net/img/categories_v2/parks_outdoors/park_\",\n" +
                "            \"suffix\": \".png\"\n" +
                "          },\n" +
                "          \"primary\": true\n" +
                "        }\n" +
                "      ],\n" +
                "      \"verified\": true,\n" +
                "      \"stats\": {\n" +
                "        \"checkinsCount\": 364591,\n" +
                "        \"usersCount\": 311634,\n" +
                "        \"tipCount\": 1583,\n" +
                "        \"visitsCount\": 854553\n" +
                "      },\n" +
                "      \"url\": \"http://www.centralparknyc.org\",\n" +
                "      \"likes\": {\n" +
                "        \"count\": 17370,\n" +
                "        \"summary\": \"17370 Likes\"\n" +
                "      },\n" +
                "      \"rating\": 9.8,\n" +
                "      \"ratingColor\": \"00B551\",\n" +
                "      \"ratingSignals\": 18854,\n" +
                "      \"beenHere\": {\n" +
                "        \"count\": 0,\n" +
                "        \"unconfirmedCount\": 0,\n" +
                "        \"marked\": false,\n" +
                "        \"lastCheckinExpiredAt\": 0\n" +
                "      },\n" +
                "      \"photos\": {\n" +
                "        \"count\": 26681,\n" +
                "        \"groups\": [\n" +
                "          {\n" +
                "            \"type\": \"venue\",\n" +
                "            \"name\": \"Venue photos\",\n" +
                "            \"count\": 26681,\n" +
                "            \"items\": [\n" +
                "              {\n" +
                "                \"id\": \"513bd223e4b0e8ef8292ee54\",\n" +
                "                \"createdAt\": 1362874915,\n" +
                "                \"source\": {\n" +
                "                  \"name\": \"Instagram\",\n" +
                "                  \"url\": \"http://instagram.com\"\n" +
                "                },\n" +
                "                \"prefix\": \"https://igx.4sqi.net/img/general/\",\n" +
                "                \"suffix\": \"/655018_Zp3vA90Sy4IIDApvfAo5KnDItoV0uEDZeST7bWT-qzk.jpg\",\n" +
                "                \"width\": 612,\n" +
                "                \"height\": 612,\n" +
                "                \"user\": {\n" +
                "                  \"id\": \"123456\",\n" +
                "                  \"firstName\": \"John\",\n" +
                "                  \"lastName\": \"Doe\",\n" +
                "                  \"gender\": \"male\"\n" +
                "                },\n" +
                "                \"visibility\": \"public\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"description\": \"Central Park is the 843-acre green heart of Manhattan and is maintained by the Central Park Conservancy. It was designed in the 19th century by Frederick Law Olmsted and Calvert Vaux as an urban escape for New Yorkers, and now receives over 40 million visits per year.\",\n" +
                "      \"storeId\": \"\",\n" +
                "      \"page\": {\n" +
                "        \"pageInfo\": {\n" +
                "          \"description\": \"The mission of the Central Park Conservancy, a private non-profit, is to restore, manage, and enhance Central Park, in partnership with the public.\",\n" +
                "          \"banner\": \"https://is1.4sqi.net/userpix/HS2JAA2IAAAR2WZO.jpg\",\n" +
                "          \"links\": {\n" +
                "            \"count\": 1,\n" +
                "            \"items\": [\n" +
                "              {\n" +
                "                \"url\": \"http://www.centralparknyc.org\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        },\n" +
                "        \"user\": {\n" +
                "          \"id\": \"29060351\",\n" +
                "          \"firstName\": \"Central Park\",\n" +
                "          \"gender\": \"none\",\n" +
                "          \"photo\": {\n" +
                "            \"prefix\": \"https://igx.4sqi.net/img/user/\",\n" +
                "            \"suffix\": \"/PCPGGJ2N3ULA5O05.jpg\"\n" +
                "          },\n" +
                "          \"type\": \"chain\",\n" +
                "          \"tips\": {\n" +
                "            \"count\": 37\n" +
                "          },\n" +
                "          \"lists\": {\n" +
                "            \"groups\": [\n" +
                "              {\n" +
                "                \"type\": \"created\",\n" +
                "                \"count\": 2,\n" +
                "                \"items\": []\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          \"homeCity\": \"New York, NY\",\n" +
                "          \"bio\": \"\",\n" +
                "          \"contact\": {\n" +
                "            \"twitter\": \"centralparknyc\",\n" +
                "            \"facebook\": \"37965424481\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"hereNow\": {\n" +
                "        \"count\": 16,\n" +
                "        \"summary\": \"16 people are here\",\n" +
                "        \"groups\": [\n" +
                "          {\n" +
                "            \"type\": \"others\",\n" +
                "            \"name\": \"Other people here\",\n" +
                "            \"count\": 16,\n" +
                "            \"items\": []\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"createdAt\": 1093478400,\n" +
                "      \"tips\": {\n" +
                "        \"count\": 1583,\n" +
                "        \"groups\": [\n" +
                "          {\n" +
                "            \"type\": \"others\",\n" +
                "            \"name\": \"All tips\",\n" +
                "            \"count\": 1583,\n" +
                "            \"items\": [\n" +
                "              {\n" +
                "                \"id\": \"5150464ee4b02f70eb28eee4\",\n" +
                "                \"createdAt\": 1364215374,\n" +
                "                \"text\": \"Did you know? To create that feeling of being in the countryside, and not in the middle of a city, the four Transverse Roads were sunken down eight feet below the park’s surface.\",\n" +
                "                \"type\": \"user\",\n" +
                "                \"canonicalUrl\": \"https://foursquare.com/item/5150464ee4b02f70eb28eee4\",\n" +
                "                \"photo\": {\n" +
                "                  \"id\": \"5150464f52625adbe29d04c2\",\n" +
                "                  \"createdAt\": 1364215375,\n" +
                "                  \"source\": {\n" +
                "                    \"name\": \"Foursquare Web\",\n" +
                "                    \"url\": \"https://foursquare.com\"\n" +
                "                  },\n" +
                "                  \"prefix\": \"https://igx.4sqi.net/img/general/\",\n" +
                "                  \"suffix\": \"/13764780_Ao02DfJpgG1ar2PfgP51hOKWsn38iai8bsSpzKd0GcM.jpg\",\n" +
                "                  \"width\": 800,\n" +
                "                  \"height\": 542,\n" +
                "                  \"visibility\": \"public\"\n" +
                "                },\n" +
                "                \"photourl\": \"https://igx.4sqi.net/img/general/original/13764780_Ao02DfJpgG1ar2PfgP51hOKWsn38iai8bsSpzKd0GcM.jpg\",\n" +
                "                \"lang\": \"en\",\n" +
                "                \"likes\": {\n" +
                "                  \"count\": 247,\n" +
                "                  \"groups\": [\n" +
                "                    {\n" +
                "                      \"type\": \"others\",\n" +
                "                      \"count\": 247,\n" +
                "                      \"items\": []\n" +
                "                    }\n" +
                "                  ],\n" +
                "                  \"summary\": \"247 likes\"\n" +
                "                },\n" +
                "                \"logView\": true,\n" +
                "                \"agreeCount\": 246,\n" +
                "                \"disagreeCount\": 0,\n" +
                "                \"todo\": {\n" +
                "                  \"count\": 30\n" +
                "                },\n" +
                "                \"user\": {\n" +
                "                  \"id\": \"13764780\",\n" +
                "                  \"firstName\": \"City of New York\",\n" +
                "                  \"gender\": \"none\",\n" +
                "                  \"photo\": {\n" +
                "                    \"prefix\": \"https://igx.4sqi.net/img/user/\",\n" +
                "                    \"suffix\": \"/2X1FKJPUY3DGRRK3.png\"\n" +
                "                  },\n" +
                "                  \"type\": \"page\"\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"id\": \"522afa5b11d2740e9aeeb336\",\n" +
                "                \"createdAt\": 1378548315,\n" +
                "                \"text\": \"Lots of squirrels in the park! パーク内にはリスがたくさんいます！しかも思ったよりデカイです。\",\n" +
                "                \"type\": \"user\",\n" +
                "                \"logView\": true,\n" +
                "                \"editedAt\": 1399418942,\n" +
                "                \"agreeCount\": 61,\n" +
                "                \"disagreeCount\": 0,\n" +
                "                \"todo\": {\n" +
                "                  \"count\": 1\n" +
                "                },\n" +
                "                \"user\": {\n" +
                "                  \"id\": \"5053872\",\n" +
                "                  \"firstName\": \"Nnkoji\",\n" +
                "                  \"gender\": \"male\",\n" +
                "                  \"photo\": {\n" +
                "                    \"prefix\": \"https://igx.4sqi.net/img/user/\",\n" +
                "                    \"suffix\": \"/5053872-DUZ51RAOUVH3GU33.jpg\"\n" +
                "                  }\n" +
                "                },\n" +
                "                \"authorInteractionType\": \"liked\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"id\": \"4cd5bda1b6962c0fd19c2e96\",\n" +
                "                \"createdAt\": 1289076129,\n" +
                "                \"text\": \"PHOTO: 1975 was the last year the New York City marathon was raced entirely inside Central Park. In this photo, runners at the marathon starting line.\",\n" +
                "                \"type\": \"user\",\n" +
                "                \"url\": \"http://www.nydailynewspix.com/sales/largeview.php?name=87g0km0g.jpg&id=152059&lbx=-1&return_page=searchResults.php&page=2\",\n" +
                "                \"canonicalUrl\": \"https://foursquare.com/item/4cd5bda1b6962c0fd19c2e96\",\n" +
                "                \"lang\": \"en\",\n" +
                "                \"likes\": {\n" +
                "                  \"count\": 26,\n" +
                "                  \"groups\": [\n" +
                "                    {\n" +
                "                      \"type\": \"others\",\n" +
                "                      \"count\": 26,\n" +
                "                      \"items\": []\n" +
                "                    }\n" +
                "                  ],\n" +
                "                  \"summary\": \"26 likes\"\n" +
                "                },\n" +
                "                \"logView\": true,\n" +
                "                \"agreeCount\": 25,\n" +
                "                \"disagreeCount\": 0,\n" +
                "                \"todo\": {\n" +
                "                  \"count\": 16\n" +
                "                },\n" +
                "                \"user\": {\n" +
                "                  \"id\": \"1241858\",\n" +
                "                  \"firstName\": \"The New York Daily News\",\n" +
                "                  \"gender\": \"none\",\n" +
                "                  \"photo\": {\n" +
                "                    \"prefix\": \"https://igx.4sqi.net/img/user/\",\n" +
                "                    \"suffix\": \"/3EV01452MGIUWBAQ.jpg\"\n" +
                "                  },\n" +
                "                  \"type\": \"page\"\n" +
                "                }\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"shortUrl\": \"http://4sq.com/2UsPUp\",\n" +
                "      \"timeZone\": \"America/New_York\",\n" +
                "      \"listed\": {\n" +
                "        \"count\": 5731,\n" +
                "        \"groups\": [\n" +
                "          {\n" +
                "            \"type\": \"others\",\n" +
                "            \"name\": \"Lists from other people\",\n" +
                "            \"count\": 5731,\n" +
                "            \"items\": [\n" +
                "              {\n" +
                "                \"id\": \"4fad24a2e4b0bcc0c18be03c\",\n" +
                "                \"name\": \"101 places to see in Manhattan before you die\",\n" +
                "                \"description\": \"Best spots to see in Manhattan (New York City) as restaurants, monuments and public spaces. Enjoy!\",\n" +
                "                \"type\": \"others\",\n" +
                "                \"user\": {\n" +
                "                  \"id\": \"356747\",\n" +
                "                  \"firstName\": \"John\",\n" +
                "                  \"lastName\": \"Doe\",\n" +
                "                  \"gender\": \"male\",\n" +
                "                  \"photo\": {\n" +
                "                    \"prefix\": \"https://igx.4sqi.net/img/user/\",\n" +
                "                    \"suffix\": \"/356747-WQOTM2ASOIERONL3.jpg\"\n" +
                "                  }\n" +
                "                },\n" +
                "                \"editable\": false,\n" +
                "                \"public\": true,\n" +
                "                \"collaborative\": false,\n" +
                "                \"url\": \"/boke/list/101-places-to-see-in-manhattan-before-you-die\",\n" +
                "                \"canonicalUrl\": \"https://foursquare.com/boke/list/101-places-to-see-in-manhattan-before-you-die\",\n" +
                "                \"createdAt\": 1336747170,\n" +
                "                \"updatedAt\": 1406242886,\n" +
                "                \"photo\": {\n" +
                "                  \"id\": \"4fa97b0c121d8a3faef6f2df\",\n" +
                "                  \"createdAt\": 1336507148,\n" +
                "                  \"prefix\": \"https://igx.4sqi.net/img/general/\",\n" +
                "                  \"suffix\": \"/IcmBihQCVr4Zt0Vxt9l237NHv--nxg1Z5_8QIMjeD8E.jpg\",\n" +
                "                  \"width\": 325,\n" +
                "                  \"height\": 487,\n" +
                "                  \"user\": {\n" +
                "                    \"id\": \"13125997\",\n" +
                "                    \"firstName\": \"IWalked Audio Tours\",\n" +
                "                    \"gender\": \"none\",\n" +
                "                    \"photo\": {\n" +
                "                      \"prefix\": \"https://igx.4sqi.net/img/user/\",\n" +
                "                      \"suffix\": \"/KZCTVBJ0FXUHSQA5.jpg\"\n" +
                "                    },\n" +
                "                    \"type\": \"page\"\n" +
                "                  },\n" +
                "                  \"visibility\": \"public\"\n" +
                "                },\n" +
                "                \"followers\": {\n" +
                "                  \"count\": 944\n" +
                "                },\n" +
                "                \"listItems\": {\n" +
                "                  \"count\": 101,\n" +
                "                  \"items\": [\n" +
                "                    {\n" +
                "                      \"id\": \"t4b67904a70c603bb845291b4\",\n" +
                "                      \"createdAt\": 1336747293,\n" +
                "                      \"photo\": {\n" +
                "                        \"id\": \"4faa9dd9e4b01bd5523d1de8\",\n" +
                "                        \"createdAt\": 1336581593,\n" +
                "                        \"prefix\": \"https://igx.4sqi.net/img/general/\",\n" +
                "                        \"suffix\": \"/KaAuGPKMZev1Te0uucRYHk92RiULGj3-GYWkX_zXbjM.jpg\",\n" +
                "                        \"width\": 720,\n" +
                "                        \"height\": 532,\n" +
                "                        \"visibility\": \"public\"\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"phrases\": [\n" +
                "        {\n" +
                "          \"phrase\": \"parque todo\",\n" +
                "          \"sample\": {\n" +
                "            \"entities\": [\n" +
                "              {\n" +
                "                \"indices\": [\n" +
                "                  22,\n" +
                "                  33\n" +
                "                ],\n" +
                "                \"type\": \"keyPhrase\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"text\": \"... a ponta, curtir o parque todo, sem pressa, admirando cada lugar. Se puder...\"\n" +
                "          },\n" +
                "          \"count\": 4\n" +
                "        }\n" +
                "      ],\n" +
                "      \"hours\": {\n" +
                "        \"status\": \"Open until 1:00 AM\",\n" +
                "        \"isOpen\": true,\n" +
                "        \"isLocalHoliday\": false,\n" +
                "        \"timeframes\": [\n" +
                "          {\n" +
                "            \"days\": \"Mon–Sun\",\n" +
                "            \"includesToday\": true,\n" +
                "            \"open\": [\n" +
                "              {\n" +
                "                \"renderedTime\": \"6:00 AM–1:00 AM\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"segments\": []\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"popular\": {\n" +
                "        \"status\": \"Likely open\",\n" +
                "        \"isOpen\": true,\n" +
                "        \"isLocalHoliday\": false,\n" +
                "        \"timeframes\": [\n" +
                "          {\n" +
                "            \"days\": \"Tue–Thu\",\n" +
                "            \"open\": [\n" +
                "              {\n" +
                "                \"renderedTime\": \"Noon–8:00 PM\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"segments\": []\n" +
                "          },\n" +
                "          {\n" +
                "            \"days\": \"Fri\",\n" +
                "            \"open\": [\n" +
                "              {\n" +
                "                \"renderedTime\": \"11:00 AM–7:00 PM\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"segments\": []\n" +
                "          },\n" +
                "          {\n" +
                "            \"days\": \"Sat\",\n" +
                "            \"open\": [\n" +
                "              {\n" +
                "                \"renderedTime\": \"8:00 AM–8:00 PM\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"segments\": []\n" +
                "          },\n" +
                "          {\n" +
                "            \"days\": \"Sun\",\n" +
                "            \"open\": [\n" +
                "              {\n" +
                "                \"renderedTime\": \"8:00 AM–7:00 PM\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"segments\": []\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"pageUpdates\": {\n" +
                "        \"count\": 12,\n" +
                "        \"items\": []\n" +
                "      },\n" +
                "      \"inbox\": {\n" +
                "        \"count\": 0,\n" +
                "        \"items\": []\n" +
                "      },\n" +
                "      \"venueChains\": [],\n" +
                "      \"attributes\": {\n" +
                "        \"groups\": [\n" +
                "          {\n" +
                "            \"type\": \"payments\",\n" +
                "            \"name\": \"Credit Cards\",\n" +
                "            \"summary\": \"No Credit Cards\",\n" +
                "            \"count\": 7,\n" +
                "            \"items\": [\n" +
                "              {\n" +
                "                \"displayName\": \"Credit Cards\",\n" +
                "                \"displayValue\": \"No\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"bestPhoto\": {\n" +
                "        \"id\": \"513bd223e4b0e8ef8292ee54\",\n" +
                "        \"createdAt\": 1362874915,\n" +
                "        \"source\": {\n" +
                "          \"name\": \"Instagram\",\n" +
                "          \"url\": \"http://instagram.com\"\n" +
                "        },\n" +
                "        \"prefix\": \"https://igx.4sqi.net/img/general/\",\n" +
                "        \"suffix\": \"/655018_Zp3vA90Sy4IIDApvfAo5KnDItoV0uEDZeST7bWT-qzk.jpg\",\n" +
                "        \"width\": 612,\n" +
                "        \"height\": 612,\n" +
                "        \"visibility\": \"public\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}"
        const val EXPECTED = "-"
    }

    private val mediaType = MediaType.get("application/json")


    @Test
    fun convertTest() {
        val responseBody = ResponseBody.create(mediaType, API_RESPONSE)
        val response = VenueDetailsConverter().convert(responseBody)

        Assert.assertNotNull(response)
        Assert.assertEquals("Central Park", response?.name)
        Assert.assertEquals("(212) 310-6600", response?.formattedPhone)
        Assert.assertEquals("59th St to 110th St (5th Ave to Central Park West)\n" +
                "New York, NY 10028\n" +
                "United States\n", response?.formattedAddress)
        Assert.assertEquals("Open until 1:00 AM", response?.hours)
        Assert.assertEquals("Park", response?.categories?.trim())
        Assert.assertEquals("-", response?.price)
        Assert.assertEquals(9, response?.rating)
        Assert.assertEquals("https://igx.4sqi.net/img/general/500x500/655018_" +
                "Zp3vA90Sy4IIDApvfAo5KnDItoV0uEDZeST7bWT-qzk.jpg", response?.photoUrl)
    }

    @Test
    fun convertWrongResponse() {
        val responseBody = ResponseBody.create(mediaType, VenuesSearchConverterTest.API_RESPONSE)
        val response = VenueDetailsConverter().convert(responseBody)

        Assert.assertNotNull(response)
        Assert.assertEquals(EXPECTED, response?.name)
        Assert.assertEquals(EXPECTED, response?.formattedPhone)
        Assert.assertEquals(EXPECTED, response?.formattedAddress)
        Assert.assertEquals(EXPECTED, response?.hours)
        Assert.assertEquals(EXPECTED, response?.categories?.trim())
        Assert.assertEquals(EXPECTED, response?.price)
        Assert.assertEquals(0, response?.rating)
        Assert.assertEquals(EXPECTED, response?.photoUrl)
    }
}