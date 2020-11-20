<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:o="urn:schemas-microsoft-com:office:office"
          xmlns:x="urn:schemas-microsoft-com:office:excel"
          xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:html="http://www.w3.org/TR/REC-html40">

    <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
        <AllowPNG/>
    </OfficeDocumentSettings>
    <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
        <WindowHeight>12105</WindowHeight>
        <WindowWidth>28800</WindowWidth>
        <WindowTopX>32767</WindowTopX>
        <WindowTopY>32767</WindowTopY>
        <ProtectStructure>False</ProtectStructure>
        <ProtectWindows>False</ProtectWindows>
    </ExcelWorkbook>
    <Styles>
        <Style ss:ID="Default" ss:Name="Normal">
            <Alignment ss:Vertical="Center"/>
                                            <Borders/>
                                                     <Font ss:FontName="等线" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
                                                                                                                            <Interior/>
                                                                                                                                      <NumberFormat/>
                                                                                                                                                    <Protection/>
        </Style>
        <Style ss:ID="s62">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
                                                                   <Borders>
                                                                   <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                        <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                           <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                                               <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                                                                                                                 </Borders>
                                                                                                                                                                                                                                                                                                                                                   <Font ss:FontName="Microsoft YaHei Bold" x:Family="Roman" ss:Size="11"
            ss:Color="#000000" ss:Bold="1"/>
                                           <Interior ss:Color="#D9E1F2" ss:Pattern="Solid"/>
        </Style>
        <Style ss:ID="s66">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
                                                                   <Borders>
                                                                   <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                        <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                           <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                                               <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                                                                                                                 </Borders>
                                                                                                                                                                                                                                                                                                                                                   <Font ss:FontName="Microsoft YaHei Regular" x:Family="Roman" ss:Size="11"
            ss:Color="#000000"/>
        </Style>
    </Styles>
    <Worksheet ss:Name="Sheet1">
        <Table ss:ExpandedColumnCount="4" ss:ExpandedRowCount="${employeeList?size}" x:FullColumns="1"
               x:FullRows="1" ss:DefaultColumnWidth="78" ss:DefaultRowHeight="14.25">
            <Row>
                <Cell ss:StyleID="s62"><Data ss:Type="String">序号</Data></Cell>
                <Cell ss:StyleID="s62"><Data ss:Type="String">姓名</Data></Cell>
                <Cell ss:StyleID="s62"><Data ss:Type="String">所属部门</Data></Cell>
                <Cell ss:StyleID="s62"><Data ss:Type="String">未填写日期</Data></Cell>
            </Row>
            <#list employeeList as employee>
            <Row ss:Height="15">
                <Cell ss:StyleID="s66"><Data ss:Type="Number">${employee.number}</Data></Cell>
                <Cell ss:StyleID="s66"><Data ss:Type="String">${employee.name}</Data></Cell>
                <Cell ss:StyleID="s66"><Data ss:Type="String">${employee.department}</Data></Cell>
                <Cell ss:StyleID="s66"><Data ss:Type="String">${employee.lackDate}</Data></Cell>
            </Row>
            </#list>

        </Table>
        <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
            <PageSetup>
                <Header x:Margin="0.3"/>
                <Footer x:Margin="0.3"/>
                <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
            </PageSetup>
            <Print>
                <ValidPrinterInfo/>
                <PaperSizeIndex>9</PaperSizeIndex>
                <HorizontalResolution>600</HorizontalResolution>
                <VerticalResolution>600</VerticalResolution>
            </Print>
            <Selected/>
            <Panes>
                <Pane>
                    <Number>3</Number>
                    <ActiveRow>1</ActiveRow>
                    <ActiveCol>3</ActiveCol>
                </Pane>
            </Panes>
            <ProtectObjects>False</ProtectObjects>
            <ProtectScenarios>False</ProtectScenarios>
        </WorksheetOptions>
    </Worksheet>
</Workbook>
