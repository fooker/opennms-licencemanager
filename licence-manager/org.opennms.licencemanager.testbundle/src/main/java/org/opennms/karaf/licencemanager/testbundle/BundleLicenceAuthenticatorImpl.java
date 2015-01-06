package org.opennms.karaf.licencemanager.testbundle;

import org.opennms.karaf.licencemgr.LicenceAuthenticatorImpl;
import org.opennms.karaf.licencemgr.LicenceService;

public class BundleLicenceAuthenticatorImpl extends LicenceAuthenticatorImpl implements BundleLicenceAuthenticator {

	/*
@Test AgenerateKeys() aesSecretKeyStr=FC13C81D9C4690A335FA4289CC54C591
@Test generateKeys() privateKeyStr=e2670200057d9d0f4b92c465d8b195c6573e329af04efe13f44dcbbafdaf101ddf4ca1a9fdde0861087d2daef38ada09ef82ea5260dd9b5c08cf390cac188c9012e30ea70ef611b756f23e27abc2829fc453c3077da2390253b8a6b9a82aaa86483ea5189ed0f850c60a7bdd91f7f98a02d4d46a245de38178b02bb9086b11c23519e18a505d1141e6d37e81fa4191ac54f1aad085181fd0b8a5af83c4d71ae0abe2fb80d6ab0cab9196965b5aec8e03739ad57c3bbe948000d0a0ab4e29c6d4a7b24e556c7cae5c1e777057f193a4c289192b110d4f7fc26b635df5a88c6383a0b40924174faa5f1e542fb55449c9722000a640bbe2440a3b6176c486f04ef3-cc6d8678ce7c65dcf1b84c6e4a03cfe18fd5318c85804e1b6674f8889798034d75db93fe12a4f19bec365b91a586b2ea55c23822ba245fabf2a86a32ef121b99dbf3f9bf0edb5193e286496938c6217c71a1d0706b1897b16a3749144aac7bdd544e7c7486b2da04d54d921ab73ad9afac6ed07e26e8400d2014b2e6189bd5237c09574901c71c4a75898b9244bb899bfd7c420ad5399f71ccaae649fb979af621fa1db9b3006e2fd192f358a381b3d6d48bf190f38b5c4d9f784d5b99ba415c118a7ad9c58e45a48288beff7190e8988b36b0ef76a77fe8b5875460fabd79d5907b2b59607272346bfcd980adc4b94e059e46dc05a450f09df06df942d49d01
@Test generateKeys() publicKeyStr=e2670200057d9d0f4b92c465d8b195c6573e329af04efe13f44dcbbafdaf101ddf4ca1a9fdde0861087d2daef38ada09ef82ea5260dd9b5c08cf390cac188c9012e30ea70ef611b756f23e27abc2829fc453c3077da2390253b8a6b9a82aaa86483ea5189ed0f850c60a7bdd91f7f98a02d4d46a245de38178b02bb9086b11c23519e18a505d1141e6d37e81fa4191ac54f1aad085181fd0b8a5af83c4d71ae0abe2fb80d6ab0cab9196965b5aec8e03739ad57c3bbe948000d0a0ab4e29c6d4a7b24e556c7cae5c1e777057f193a4c289192b110d4f7fc26b635df5a88c6383a0b40924174faa5f1e542fb55449c9722000a640bbe2440a3b6176c486f04ef3-10001
@Test generateKeys() privateKeyEnryptedStr=469CFA20A6C795881BAA111C4C6FC248AD1FB0968A72D26A76B6974A1146DC9F2CF4F66ED9558CFA768E29C84BCA8A1C5D23EB5EFDD418051D92A01EA3B8617B2A5DF8A2E8A9F7BB113D7566B6FEF19104EC3C64277B60B586464EF0BEEE31434A8886DD2BAA1A04A242E33A91287CD6BCDBDFE45F3532B262AE1161E3CD9B4D93126C62938AAAC769BEFA65801264AA2C8EB5C0E8232DD65211C3F9ABEF6637918F13BF604D785FDB9CAB0CCBC3706D7D171DFDA41C51B3F8455D9719043509E1749D76E93B908DE63784EB94DB50764F58DD772477F845EED95EBBB35E1639F749824F18B1CA9C4A7D0175BDF7CF425DCDAB85F84362F06A4C9B3CF9FB0ACCD92CB24947A658A5FFCB84DFF9BC6DD9406B100D5ED57064899D4CA69F24C6777782BB78110D6AFB677D764061C3F0E9DB0471C481788D85A6CEFDBD1F5DEE801AB1CA6D6541941D26C761890F7DDD8C9A77EE04F67C1FBF1DA9514C1161FB79D7C4A64FE2609F35C01C672D3F45EB2F2CBBA42E337270F7A4C819B820EFD31CC5949E0C0E636B4BC0612705E7C8B0D68D7F6896CC9035A3A89C807E82B9470C234AE88CF3E1458ABC83FFF5A6A2C13E779CDEBDFA994E64A89493ECDED7F4C27B1FB9A35F125B1FC8623F32CD1F12BA5BB92571E0B7ED1814295E03D1F5321949E60C8D47199033E4ECCA84A220ADACBF0A4EE94590EBDEE61EC4C22E6535CAD1B750C67BD913932E218BFDB88AD1157F0D247F4A45D250189AE1021F1A25BA7193E921763CAF121D224D353CCBDD6D0A88402A303CE15381B7AA21FC5B1E1C3B18E89B8DB044747CD41149AA1C908BA3C5240A043823D32F4D6CA42562AF9F0E78178A223528A442523CACACF8DA27361EA9DD25D54BBD2C7C9A916D11D51F75C300A54AAC5722239F2F2E8BA2B4D41FD3002FC47F0972C5AAFF0F7686B66A554E3A2C94766CE145DFAB42D0D57DA12ED428DD8EFC72F774452FE2DD38DF4BAA7025AA38737216045004BDF104B14EB3088250133E9257654DCB83CA507A983F882150CE6CF292F7CCF0D6275825655C8F49EB365FD07EF4DE2628D7887A5E59899B02C173F8411386B228F3BEC416629D9C4AC2CAAA61E53715A52859CDD6F6AAAD6EE62214CDD009B026372EE77A31258FC4379026B0A8253E77A28A5FE09063A233EBD0C24181A6FBD51F2677A0643944C05F60537071F8A781BDF2AE6F5488A98E710EDA243C00652B75E1996D9E6DE3EC2BD04B11EBAE9D5E3385DB8DCB290339F5658BFE2D1E88832710889EEB895E019E61716284EA7BB677A8CE7B824610D935DB18632F7643C820A213AAAF4BC780AA12BDA589785031E618C9E8E3363CA62CEB2438393C7FE3E6E1EDE128F3BB791D1B6FA6A0A6463BBC319BFEBD4C2CAEDC98A87CF42A698545B369F0F676FBF7C7E5950169782AB27FF1B0F7CC1AFD3BF8DEEE1B557E90328DB1640CEA5E8F846AB7B06C6C22D6FB87F0716B
@Test AgenerateKeys() END
@Test BencodeLicence() Start
@Test BencodeLicence licenceMetadataHashStr=780258AAED88CD56A947D5BF8B669237B4B9C8080A9FCC5B02A75432014D7891
@Test BencodeLicence encryptedHashStr=53D5D625DDC524F44C223222233F6A024A7F41711A50E8DC4D16F6510B25842A815C43D2CFA5EA9693D3485BD53BEA179D49FF836F928B7700C471CAEB8FD26CE151CAAA649753F413309B43B2172F33D31EC603E3F4F7828638A7BC5377D7AF4CA8F7E3DD01BA2645DE2991B093E2B11D09E562D54C65BA45F3BE4142DACF8BB82EC2CA6891557D74D34B46729009083FA9C0709CDB17E6DB3FFD35B2BF0C59A31D2C867DBC909BA4A31FF8766F4412263ECBB5FF6C484B1B823B32EAA9570C9EE04BA526F066747143AC281E506DE2622CF2EF980B804E4F31A1196DD8AB411606FC1A4E310A4E0D5C1361221163914676F9E8AA10C65333C3C8DB17F188C5
@Test BencodeLicence licenceStr=3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D22796573223F3E3C4C6963656E63654D657461646174613E3C70726F6475637449643E6F72672E6F70656E6E6D732F6F72672E6F70656E6E6D732E6B617261662E6C6963656E63656D616E616765722E7465737462756E646C652F312E302D534E415053484F543C2F70726F6475637449643E3C6C6963656E7365653E4D722043726169672047616C6C656E3C2F6C6963656E7365653E3C6C6963656E736F723E4F70656E4E4D5320554B3C2F6C6963656E736F723E3C73797374656D49643E346164373261333465333633356331622D39396461333332333C2F73797374656D49643E3C7374617274446174653E323031352D30312D30365431323A31363A30362E3337315A3C2F7374617274446174653E3C657870697279446174653E323031352D30312D30365431323A31363A30362E3337315A3C2F657870697279446174653E3C6F7074696F6E733E3C6F7074696F6E3E3C6465736372697074696F6E3E7468697320697320746865206465736372697074696F6E3C2F6465736372697074696F6E3E3C6E616D653E6E65776E616D653C2F6E616D653E3C76616C75653E6E657776616C75653C2F76616C75653E3C2F6F7074696F6E3E3C2F6F7074696F6E733E3C2F4C6963656E63654D657461646174613E:53D5D625DDC524F44C223222233F6A024A7F41711A50E8DC4D16F6510B25842A815C43D2CFA5EA9693D3485BD53BEA179D49FF836F928B7700C471CAEB8FD26CE151CAAA649753F413309B43B2172F33D31EC603E3F4F7828638A7BC5377D7AF4CA8F7E3DD01BA2645DE2991B093E2B11D09E562D54C65BA45F3BE4142DACF8BB82EC2CA6891557D74D34B46729009083FA9C0709CDB17E6DB3FFD35B2BF0C59A31D2C867DBC909BA4A31FF8766F4412263ECBB5FF6C484B1B823B32EAA9570C9EE04BA526F066747143AC281E506DE2622CF2EF980B804E4F31A1196DD8AB411606FC1A4E310A4E0D5C1361221163914676F9E8AA10C65333C3C8DB17F188C5:FC13C81D9C4690A335FA4289CC54C591
@Test BencodeLicence() licenceStringPlusCrc=3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D22796573223F3E3C4C6963656E63654D657461646174613E3C70726F6475637449643E6F72672E6F70656E6E6D732F6F72672E6F70656E6E6D732E6B617261662E6C6963656E63656D616E616765722E7465737462756E646C652F312E302D534E415053484F543C2F70726F6475637449643E3C6C6963656E7365653E4D722043726169672047616C6C656E3C2F6C6963656E7365653E3C6C6963656E736F723E4F70656E4E4D5320554B3C2F6C6963656E736F723E3C73797374656D49643E346164373261333465333633356331622D39396461333332333C2F73797374656D49643E3C7374617274446174653E323031352D30312D30365431323A31363A30362E3337315A3C2F7374617274446174653E3C657870697279446174653E323031352D30312D30365431323A31363A30362E3337315A3C2F657870697279446174653E3C6F7074696F6E733E3C6F7074696F6E3E3C6465736372697074696F6E3E7468697320697320746865206465736372697074696F6E3C2F6465736372697074696F6E3E3C6E616D653E6E65776E616D653C2F6E616D653E3C76616C75653E6E657776616C75653C2F76616C75653E3C2F6F7074696F6E3E3C2F6F7074696F6E733E3C2F4C6963656E63654D657461646174613E:53D5D625DDC524F44C223222233F6A024A7F41711A50E8DC4D16F6510B25842A815C43D2CFA5EA9693D3485BD53BEA179D49FF836F928B7700C471CAEB8FD26CE151CAAA649753F413309B43B2172F33D31EC603E3F4F7828638A7BC5377D7AF4CA8F7E3DD01BA2645DE2991B093E2B11D09E562D54C65BA45F3BE4142DACF8BB82EC2CA6891557D74D34B46729009083FA9C0709CDB17E6DB3FFD35B2BF0C59A31D2C867DBC909BA4A31FF8766F4412263ECBB5FF6C484B1B823B32EAA9570C9EE04BA526F066747143AC281E506DE2622CF2EF980B804E4F31A1196DD8AB411606FC1A4E310A4E0D5C1361221163914676F9E8AA10C65333C3C8DB17F188C5:FC13C81D9C4690A335FA4289CC54C591-d2e23c34
	 */
	// to test use command
	// licence-mgr:add 3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D22796573223F3E3C4C6963656E63654D657461646174613E3C70726F6475637449643E6F72672E6F70656E6E6D732F6F72672E6F70656E6E6D732E6B617261662E6C6963656E63656D616E616765722E7465737462756E646C652F312E302D534E415053484F543C2F70726F6475637449643E3C6C6963656E7365653E4D722043726169672047616C6C656E3C2F6C6963656E7365653E3C6C6963656E736F723E4F70656E4E4D5320554B3C2F6C6963656E736F723E3C73797374656D49643E346164373261333465333633356331622D39396461333332333C2F73797374656D49643E3C7374617274446174653E323031352D30312D30365431323A31363A30362E3337315A3C2F7374617274446174653E3C657870697279446174653E323031352D30312D30365431323A31363A30362E3337315A3C2F657870697279446174653E3C6F7074696F6E733E3C6F7074696F6E3E3C6465736372697074696F6E3E7468697320697320746865206465736372697074696F6E3C2F6465736372697074696F6E3E3C6E616D653E6E65776E616D653C2F6E616D653E3C76616C75653E6E657776616C75653C2F76616C75653E3C2F6F7074696F6E3E3C2F6F7074696F6E733E3C2F4C6963656E63654D657461646174613E:53D5D625DDC524F44C223222233F6A024A7F41711A50E8DC4D16F6510B25842A815C43D2CFA5EA9693D3485BD53BEA179D49FF836F928B7700C471CAEB8FD26CE151CAAA649753F413309B43B2172F33D31EC603E3F4F7828638A7BC5377D7AF4CA8F7E3DD01BA2645DE2991B093E2B11D09E562D54C65BA45F3BE4142DACF8BB82EC2CA6891557D74D34B46729009083FA9C0709CDB17E6DB3FFD35B2BF0C59A31D2C867DBC909BA4A31FF8766F4412263ECBB5FF6C484B1B823B32EAA9570C9EE04BA526F066747143AC281E506DE2622CF2EF980B804E4F31A1196DD8AB411606FC1A4E310A4E0D5C1361221163914676F9E8AA10C65333C3C8DB17F188C5:FC13C81D9C4690A335FA4289CC54C591-d2e23c34
			
	//private final String challengeKeyEnryptedStr="7059105FB54DC64C21E00CED1216BF30D54852C724276AE5885068A789A1EFBA731F846BD43B2EA0BD878A271A13CEFE79EDB655C9784954DC3741DC7490C3D9C1BFEE675D7D39FF846CBCBA121533FC4883A951375EE9B6E50127A1C582CAC161D913DD44DE82966D82DD35278FB1A64AD6747C4EF177A6A5E3240C1DFD9D4C6977ED06A2328231861314CE98C5E9E0EEC35E9B2E42AC18B3415560C45E9B7255A7CA62F90972F14D5F2D90A8DCD5B2DF0DFF393DD09B46691B0206173749EA36D6ECADEF629B0E309E316F7C256ED0390444A1ADDD533ABC5F4F3371E67B1EE128AFCB829B459B4C307663D185263631B2448BCF3B4D331F6F943CAC6B612F63951FB01476C82CB7565AAB78B580B01139B13FD6EC350C688936857D579F5FB05834EF8ADDD44F150AAFEEC503E77FD847C9699689B6F53758D56475711FC2FD59F9011B0AAA2B6FEFC1F871F5843664A39F5BC8B520E94A68EBDEABB3FE6CAF95EA64C5F874C5DC2DCB234FA11F471524587AD283B63F27F70D57CBBC2D87C8E72EA5D63AE422BB5270D86314E8F939CB1CE37A30CC50CE04812A1A22590AC579B6E43C98C8F2A71D7782DBAAD44774667F760809794168FE6D8574238467846280BB1E05A8A53663A78B58AB8FCF8A3984D5ABC1179D7C39AD432D498BC78A4C44B5CB81A60D68F3AEBFFEE00344DF6456D151D0BA3E2B71D270A2C94AC3DFBF2E35592E955E6DA25FBA0EAA6D171BA7A0652BABBAD79E28CB99388DC1F736F971198CA8B28D55C2589E3501DFEDFE367199B80B5BA1FA1F476FED58A86A947B9BCAAB2A6D6A0C5E4D8F0BB00A9AA0F713BBF3FF5F4F5A117285DDD13162DE9B6970647C8B2D5EDFE85290B6079DC80F397DBE2D227A9E0076A6BE6216F5FEE2ECCFAD48333747C52771400B64290A82B4EEFF9A40DDA3DEE02DF850D827560C5352B32B1D13F3FECCF27A8B407B3685A1349985009C21A2B1C6BD27FA37CC61D2743E4245BC6185C856A619947C3DE6EF0F79D3EBF0055B86F2E93AC33FD1F7C8C16E03915FEBC4FB7EE2E658D5DCD2A0546F9F08AA224CFFB36649924A3A0365BAADDD5FE1F89F5084DA1CF513338A906F7FCD930BBDFED239144ABC645B798593482ADB3281C4785363B99F8F63BF259DC1718EB6BAE1CEC0F47420A645BCC7622CADD9C8D69EF15DCD5425C10310A6C8E8197DF58C352EB9EE2E687A8ACB1C5A78D2E06A1DD0A008E87F6A0F6BE20AC4003FCE6C8E088E66CF0A7A7B425481918FE17AE42BB236F7E1AEED8DDDF846F53F5BA2CE17C1F9384CAA9A92F4EFADF24267776AB8803890CD873874A2B342769485565D64C7C45FBBE6237311811E704CEDCD86A08F925FFB8A8D24D6E4D167F400F5D000E2E637CE5EFCC216EE84B71C0D41B39A721F22F292BA8B58BBDB85A04A3CDADB75AA6D05E1EEE58C5B4253E661A9110EB37DB26E8DEB1C39A221A9B0B86B6A7BABDA29E4B0FDCC";
	final static String productId="org.opennms/org.opennms.karaf.licencemanager.testbundle/1.0-SNAPSHOT";
	final static String privateKeyEnryptedStr="469CFA20A6C795881BAA111C4C6FC248AD1FB0968A72D26A76B6974A1146DC9F2CF4F66ED9558CFA768E29C84BCA8A1C5D23EB5EFDD418051D92A01EA3B8617B2A5DF8A2E8A9F7BB113D7566B6FEF19104EC3C64277B60B586464EF0BEEE31434A8886DD2BAA1A04A242E33A91287CD6BCDBDFE45F3532B262AE1161E3CD9B4D93126C62938AAAC769BEFA65801264AA2C8EB5C0E8232DD65211C3F9ABEF6637918F13BF604D785FDB9CAB0CCBC3706D7D171DFDA41C51B3F8455D9719043509E1749D76E93B908DE63784EB94DB50764F58DD772477F845EED95EBBB35E1639F749824F18B1CA9C4A7D0175BDF7CF425DCDAB85F84362F06A4C9B3CF9FB0ACCD92CB24947A658A5FFCB84DFF9BC6DD9406B100D5ED57064899D4CA69F24C6777782BB78110D6AFB677D764061C3F0E9DB0471C481788D85A6CEFDBD1F5DEE801AB1CA6D6541941D26C761890F7DDD8C9A77EE04F67C1FBF1DA9514C1161FB79D7C4A64FE2609F35C01C672D3F45EB2F2CBBA42E337270F7A4C819B820EFD31CC5949E0C0E636B4BC0612705E7C8B0D68D7F6896CC9035A3A89C807E82B9470C234AE88CF3E1458ABC83FFF5A6A2C13E779CDEBDFA994E64A89493ECDED7F4C27B1FB9A35F125B1FC8623F32CD1F12BA5BB92571E0B7ED1814295E03D1F5321949E60C8D47199033E4ECCA84A220ADACBF0A4EE94590EBDEE61EC4C22E6535CAD1B750C67BD913932E218BFDB88AD1157F0D247F4A45D250189AE1021F1A25BA7193E921763CAF121D224D353CCBDD6D0A88402A303CE15381B7AA21FC5B1E1C3B18E89B8DB044747CD41149AA1C908BA3C5240A043823D32F4D6CA42562AF9F0E78178A223528A442523CACACF8DA27361EA9DD25D54BBD2C7C9A916D11D51F75C300A54AAC5722239F2F2E8BA2B4D41FD3002FC47F0972C5AAFF0F7686B66A554E3A2C94766CE145DFAB42D0D57DA12ED428DD8EFC72F774452FE2DD38DF4BAA7025AA38737216045004BDF104B14EB3088250133E9257654DCB83CA507A983F882150CE6CF292F7CCF0D6275825655C8F49EB365FD07EF4DE2628D7887A5E59899B02C173F8411386B228F3BEC416629D9C4AC2CAAA61E53715A52859CDD6F6AAAD6EE62214CDD009B026372EE77A31258FC4379026B0A8253E77A28A5FE09063A233EBD0C24181A6FBD51F2677A0643944C05F60537071F8A781BDF2AE6F5488A98E710EDA243C00652B75E1996D9E6DE3EC2BD04B11EBAE9D5E3385DB8DCB290339F5658BFE2D1E88832710889EEB895E019E61716284EA7BB677A8CE7B824610D935DB18632F7643C820A213AAAF4BC780AA12BDA589785031E618C9E8E3363CA62CEB2438393C7FE3E6E1EDE128F3BB791D1B6FA6A0A6463BBC319BFEBD4C2CAEDC98A87CF42A698545B369F0F676FBF7C7E5950169782AB27FF1B0F7CC1AFD3BF8DEEE1B557E90328DB1640CEA5E8F846AB7B06C6C22D6FB87F0716B";

	public BundleLicenceAuthenticatorImpl(LicenceService licenceService) {
		super(licenceService, productId, privateKeyEnryptedStr);
	}

}