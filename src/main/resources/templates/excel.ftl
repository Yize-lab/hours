<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:o="urn:schemas-microsoft-com:office:office"
          xmlns:x="urn:schemas-microsoft-com:office:excel"
          xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:html="http://www.w3.org/TR/REC-html40">
    <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
        <Author>50437</Author>
        <LastAuthor>50437</LastAuthor>
        <Created>2015-06-05T18:17:20Z</Created>
        <LastSaved>2015-06-05T18:17:26Z</LastSaved>
        <Version>16.00</Version>
    </DocumentProperties>
    <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
        <AllowPNG/>
    </OfficeDocumentSettings>
    <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
        <WindowHeight>12645</WindowHeight>
        <WindowWidth>22260</WindowWidth>
        <WindowTopX>32767</WindowTopX>
        <WindowTopY>32767</WindowTopY>
        <ProtectStructure>False</ProtectStructure>
        <ProtectWindows>False</ProtectWindows>
    </ExcelWorkbook>
    <Styles>
        <Style ss:ID="Default" ss:Name="Normal">
            <Alignment ss:Vertical="Bottom"/>
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
                                           <Interior ss:Color="#DDEBF7" ss:Pattern="Solid"/>
        </Style>
        <Style ss:ID="s63">
            <Alignment ss:Vertical="Center"/>
                                            <Borders>
                                            <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                 <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                        <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                                                                                          </Borders>
                                                                                                                                                                                                                                                                                                                            <Font ss:FontName="Microsoft YaHei Regular" x:Family="Roman" ss:Size="11"
            ss:Color="#000000"/>
        </Style>
        <Style ss:ID="s64">
            <Alignment ss:Vertical="Center"/>
                                            <Borders>
                                            <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                 <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                        <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                                                                                          </Borders>
                                                                                                                                                                                                                                                                                                                            <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
        </Style>
        <Style ss:ID="s65">
            <Alignment ss:Vertical="Center"/>
                                            <Borders>
                                            <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                 <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                        <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
                                                                                                                                                                                                                                                                                                                          </Borders>
                                                                                                                                                                                                                                                                                                                            <Font ss:FontName="Microsoft YaHei Regular" x:Family="Roman" ss:Size="11"
            ss:Color="#000000"/>
                               <NumberFormat ss:Format="Short Date"/>
        </Style>
    </Styles>
    <Worksheet ss:Name="Sheet1">
        <Table ss:ExpandedColumnCount="4" ss:ExpandedRowCount="2" x:FullColumns="1"
               x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
            <Column ss:Index="2" ss:AutoFitWidth="0" ss:Width="99" ss:Span="2"/>
            <Row>
                <Cell ss:StyleID="s62"><Data ss:Type="String">序号</Data></Cell>
                <Cell ss:StyleID="s62"><Data ss:Type="String">姓名</Data></Cell>
                <Cell ss:StyleID="s62"><Data ss:Type="String">所属部门</Data></Cell>
                <Cell ss:StyleID="s62"><Data ss:Type="String">未填写日期</Data></Cell>
            </Row>
            <#list employeeList as employee>
            <Row ss:Height="15">
                <Cell ss:StyleID="s63"><Data ss:Type="Number">${employee.number}</Data></Cell>
                <Cell ss:StyleID="s64"><Data ss:Type="String">${employee.name}</Data></Cell>
                <Cell ss:StyleID="s64"><Data ss:Type="String">${employee.department}</Data></Cell>
                <Cell ss:StyleID="s65"><Data ss:Type="DateTime">${employee.lackDate}</Data></Cell>
            </Row>
            </#list>
        </Table>
        <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
            <PageSetup>
                <Header x:Margin="0.3"/>
                <Footer x:Margin="0.3"/>
                <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
            </PageSetup>
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
