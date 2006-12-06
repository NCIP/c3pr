<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/stylesheet.css" />
<script src="js/script.js" type="text/javascript"></script>
</head>
<body>
<table summary="" cellpadding="0" cellspacing="0" border="0"
	width="100%" height="100%">

	<!-- nci hdr begins -->
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="hdrBG">
			<tr>
				<td width="283" height="37" align="left"><a
					href="http://www.cancer.gov"><img
					alt="National Cancer Institute" src="images/logotype.gif"
					width="283" height="37" border="0"></a></td>
				<td>&nbsp;</td>
				<td width="295" height="37" align="right"><a
					href="http://www.cancer.gov"><img
					alt="U.S. National Institutes of Health | www.cancer.gov"
					src="images/tagline.gif" width="295" height="37" border="0"></a></td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- nci hdr ends -->

	<tr>
		<td height="100%" align="center" valign="top">
		<table summary="" cellpadding="0" cellspacing="0" border="0"
			height="100%" width="771">

			<!-- application hdr begins -->
			<tr>
				<td height="50">
				<table width="100%" height="50" border="0" cellspacing="0"
					cellpadding="0" class="subhdrBG">
					<tr>
						<td height="50" align="left"><a href="#"><img
							src="images/c3prLogo.jpg" alt="Application Logo" height="35"
							hspace="10" border="0"></a></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- application hdr ends -->

			<tr>
				<td valign="top">
				<table summary="" cellpadding="0" cellspacing="0" border="0"
					height="100%" width="100%">

					<!--_____ menu begins _____-->
					<tr>
						<td height="20" width="100%" class="mainMenu"><!-- main menu begins -->
						<table summary="" cellpadding="0" cellspacing="0" border="0"
							height="35" width="100%">
							<tr>
								<td height="30" valign="bottom"><!-- tabs begins -->
								<table summary="" cellpadding="0" cellspacing="0" border="0"
									width="100%">
									<tr>
										<td width="1"><!-- anchor to skip main menu --><a
											href="#content"><img src="images/shim.gif"
											alt="Skip Menu" width="1" height="1" border="0" /></a></td>

										<td class="mainMenuSpacer">&nbsp;</td>
										<td height="20" class="mainMenuItem"><a
											class="mainMenuLink" href="Home.html">Home</a></td>

										<td class="mainMenuSpacer">&nbsp;</td>
										<td height="20" class="mainMenuItemOn"><a
											class="mainMenuLink" href="ProtocolsMain.html">Protocols</a>
										</td>
										<td class="mainMenuSpacer">&nbsp;</td>
										<td height="20" class="mainMenuItem"><a
											class="mainMenuLink" href="Links.html">Links</a></td>

										<td class="mainMenuSpacer">&nbsp;</td>
										<td height="20" class="mainMenuItem"><a
											class="mainMenuLink" href="Help.html">Help</a></td>
										<td class="mainMenuSpacer">&nbsp;</td>
										<td height="20" class="mainMenuItem"><a
											class="mainMenuLink" href="Login.html">Logout</a></td>

										<td width="99%" class="mainMenuSpacer">&nbsp;</td>
									</tr>
								</table>
								<!-- tabs ends --></td>
							</tr>
							<tr>
								<td height="5" class="mainMenuSub"><!-- sub-items begin -->
								<table summary="" cellpadding="0" cellspacing="0" border="0"
									height="100%">
									<tr>
										<td class="mainMenuSubItems">&nbsp;&nbsp;</td>
									</tr>
								</table>
								<!-- sub-items end --></td>
							</tr>
						</table>
						<!-- main menu ends --></td>
					</tr>
					<!--_____ menu ends _____-->

					<!--_____ main content begins _____-->
					<c:url value="/createprotocol.do" var="formAction"/>
					<form:form method="post" action="${formAction}">

						<tr>
							<td valign="top"><!-- target of anchor to skip menus --><a
								name="content" />
							<table summary="" cellpadding="0" cellspacing="0" border="0"
								class="contentPage" width="100%" height="100%">
								<tr>
									<td valign="top">
									<table cellpadding="3" cellspacing="0" border="0"
										class="contentBegins" align="center">
										<tr>
											<td class="contentTitle">Create Protocol</td>
										</tr>
										<tr>
											<td>
											<table summary="" cellpadding="0" cellspacing="0" border="0"
												align="center" class="dataTableBorderOnly" width="750">
												<tr>
													<td class="formMessage" colspan="5">&nbsp;
													  <label>Enter atleast Protocol ID</label>
													</td>
												</tr>
												<tr>
													<td class="formTitle" height="20" colspan="5">Create
													Protocol</td>
												</tr>
												<tr>
													<td class="formRequiredNoticeWithoutBorder" width="5">&nbsp;</td>
													<td class="formLabelWithoutBorder"><form:label path="id">Protocol
													ID:</form:label></td>
													<td class="formLabelWithoutBorder"><form:label path="protocolPhase">Protocol
													Phase Code:</form:label></td>
													<td class="formLabelWithoutBorder"><form:label path="protocolType">Protocol
													Type Code:</form:label></td>
													<td class="formLabelWithoutBorder"><form:label path="multiInstitutionalFlag">Multi-Institutional:</form:label></td>
													<td class="formRequiredNoticeWithoutBorder" width="5">&nbsp;</td>
												</tr>
												<tr>
													<td class="formRequiredNoticeWithoutBorder" width="5">&nbsp;</td>
													<td class="formFieldWithoutBorder"><form:input path="id"/></td>
													<td class="formFieldWithoutBorder"><form:input path="protocolPhase"/></td>
													<td class="formFieldWithoutBorder">
													<form:select path="protocolType">
										            	<form:options items="${protocoltypecode}" itemLabel="description" itemValue="id"/>
										            </form:select></td>
										            													<td class="formFieldWithoutBorder">
													<form:select path="multiInstitutionalFlag">
										            	<form:options items="${multiInstitutional}" itemLabel="str" itemValue="str"/>
										            </form:select></td>
													<td class="formRequiredNoticeWithoutBorder" width="5">&nbsp;
													</td>
												</tr>
											

											</table>
											</td>
										</tr>
										<tr>
											<td align="left" colspan="3"><!-- action buttons begins -->
											<table cellpadding="4" cellspacing="0" border="0">
												<tr>
													<td>
													  <input class="actionButton" type="submit" value="Create Protocol" id="assign-button">
            										 </td>
												</tr>
											</table>
											<!-- action buttons end --></td>
										</tr>
									</table>
									</td>
								</tr>
							</table></td>
						</tr>

					</form:form>


					<!--_____ main content ends _____-->

					<tr>
						<td height="20" class="footerMenu"><!-- application ftr begins -->
						<table summary="" cellpadding="0" cellspacing="0" border="0"
							width="100%">
							<tr>
								<td align="center" height="20" class="footerMenuItem"<a
									class="footerMenuLink" href="contactUS.html">CONTACT US</a>&nbsp;&nbsp;
								</td>
								<td><img src="images/ftrMenuSeparator.gif" width="1"
									height="16" alt="" /></td>
								<td align="center" height="20" class="footerMenuItem"<a
									class="footerMenuLink" href="PrivacyNotice.html">PRIVACY
								NOTICE</a>&nbsp;&nbsp;</td>
								<td><img src="images/ftrMenuSeparator.gif" width="1"
									height="16" alt="" /></td>
								<td align="center" height="20" class="footerMenuItem"<a
									class="footerMenuLink" href="Disclaimer.html">DISCLAIMER</a>&nbsp;&nbsp;
								</td>
								<td><img src="images/ftrMenuSeparator.gif" width="1"
									height="16" alt="" /></td>
								<td align="center" height="20" class="footerMenuItem"<a
									class="footerMenuLink" href="Accessibility.html">ACCESSIBILITY</a>&nbsp;&nbsp;
								</td>
								<td><img src="images/ftrMenuSeparator.gif" width="1"
									height="16" alt="" /></td>
								<td align="center" height="20" class="footerMenuItem"<a
									class="footerMenuLink" href="ApplicationSupport.html">APPLICATION
								SUPPORT</a>&nbsp;&nbsp;</td>
							</tr>
						</table>
						<!-- application ftr ends --></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td><!-- footer begins -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="ftrTable">
			<tr>
				<td valign="top">
				<div align="center"><a href="http://www.cancer.gov/"><img
					src="images/footer_nci.gif" width="63" height="31"
					alt="National Cancer Institute" border="0"></a> <a
					href="http://www.dhhs.gov/"><img src="images/footer_hhs.gif"
					width="39" height="31"
					alt="Department of Health and Human Services" border="0"></a> <a
					href="http://www.nih.gov/"><img src="images/footer_nih.gif"
					width="46" height="31" alt="National Institutes of Health"
					border="0"></a> <a href="http://www.firstgov.gov/"><img
					src="images/footer_firstgov.gif" width="91" height="31"
					alt="FirstGov.gov" border="0"></a></div>
				</td>
			</tr>
		</table>
		<!-- footer ends --></td>
	</tr>
</table>
</body>
</html>
