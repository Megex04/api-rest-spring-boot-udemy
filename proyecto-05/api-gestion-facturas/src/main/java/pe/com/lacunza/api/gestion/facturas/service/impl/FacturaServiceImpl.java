package pe.com.lacunza.api.gestion.facturas.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.com.lacunza.api.gestion.facturas.constantes.FacturaConstantes;
import pe.com.lacunza.api.gestion.facturas.dao.FacturaDAO;
import pe.com.lacunza.api.gestion.facturas.pojo.Factura;
import pe.com.lacunza.api.gestion.facturas.security.jwt.JwtFilter;
import pe.com.lacunza.api.gestion.facturas.service.FacturaService;
import pe.com.lacunza.api.gestion.facturas.util.FacturaUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private FacturaDAO facturaDAO;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("Dentro del metodo generateReport() para el reporte el PDF");
        try {
            String fileName = "";
            if(validateDataRequestMap(requestMap)) {
                if(requestMap.containsKey("isGenerated") && !(Boolean) requestMap.get("isGenerated")) {
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = FacturaUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertarFactura(requestMap);
                }
                String data = "Nombre: " + requestMap.get("nombre") + "\nNumero de contacto: " + requestMap.get("numeroContacto") +
                        "\nEmail: " + requestMap.get("email") + "\nMetodo de pago: " + requestMap.get("metodoPago");

                Document pdfDocument = new Document();
                PdfWriter.getInstance(pdfDocument, new FileOutputStream(FacturaConstantes.STORED_LOCATION +"\\" +fileName + ".pdf"));

                pdfDocument.open();
                setRectangleinPdf(pdfDocument);

                Paragraph paragraphHeader = new Paragraph("Gestion de categorias y productos", getFont("Header"));
                paragraphHeader.setAlignment(Element.ALIGN_CENTER);
                paragraphHeader.setMultipliedLeading(2.0f);
                pdfDocument.add(paragraphHeader);

                pdfDocument.add(new Paragraph("\n"));

                PdfPTable pdfTable = new PdfPTable(5);
                pdfTable.setWidthPercentage(100);
                addTableHeaderPDF(pdfTable);

                JSONArray jsonArray = FacturaUtils.getJSONArrayFromString((String) requestMap.get("productoDetalles"));
                for(int i = 0; i < jsonArray.length(); i++) {
                    addRowsToPDF(pdfTable, FacturaUtils.getMapFromJson(jsonArray.getString(i)));
                }
                pdfDocument.add(pdfTable);

                Paragraph footer = new Paragraph("Total: " + requestMap.get("montoTotal") + "\nGracias por visitarnos, vuelva pronto !!!", getFont("Data"));
                pdfDocument.add(footer);
                pdfDocument.close();
                return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);
            }
            return new ResponseEntity<>("Datos requeridos no encontrados !!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error al generar el reporte", e);
        }
        return new ResponseEntity<>(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Factura>> getFacturas() {
        List<Factura> facturas = new ArrayList<>();
        if(jwtFilter.isAdmin()) {
            facturas = facturaDAO.getFacturas();
        } else {
            facturas = facturaDAO.getFacturaByUsername(jwtFilter.currentUser());
        }
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdfReport(Map<String, Object> requestMap) {
        log.info("Dentro del getPdfReport() para el reporte el PDF: {}", requestMap);
        try {
            byte[] bytesArray = new byte[0];
            if(!requestMap.containsKey("uuid") && validateDataRequestMap(requestMap)) {
                return new ResponseEntity<>(bytesArray, HttpStatus.BAD_REQUEST);
            }
            String filePath = FacturaConstantes.STORED_LOCATION +"\\" + requestMap.get("uuid") + ".pdf";
            if(FacturaUtils.isFileExists(filePath)) {
                bytesArray = getByteArrayFromFile(filePath);
                return new ResponseEntity<>(bytesArray, HttpStatus.OK);
            } else {
                requestMap.put("isGenerated", false);
                generateReport(requestMap);
                bytesArray = getByteArrayFromFile(filePath);
                return new ResponseEntity<>(bytesArray, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error al generar el reporte", e);
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteFactura(Integer id) {
        try {
            Optional<Factura> factura = facturaDAO.findById(id);
            if(factura.isPresent()) {
                facturaDAO.deleteById(id);
                return FacturaUtils.getResponseEntity("Factura eliminada con exito !!", HttpStatus.OK);
            }
            return FacturaUtils.getResponseEntity("Factura no encontrada con ese ID: "+ id +" !!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error al generar el reporte", e);
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private byte[] getByteArrayFromFile(String filePath) throws IOException {
        File initialFile = new File(filePath);
        InputStream inputStream = new FileInputStream(initialFile);
        byte[] bytesArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return bytesArray;
    }

    private void setRectangleinPdf(Document pdfDocument) throws DocumentException {
        log.info("Dentro del metodo setRectangleinPdf() para colocal rectangle en el PDF");
        Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        pdfDocument.add(rectangle);
    }
    private Font getFont(String type) {
        log.info("Dentro del metodo getFont() para la fuente del PDF");
        switch(type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.COURIER, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }
    private void addRowsToPDF(PdfPTable pdfTable, Map<String, Object> data) {
        log.info("Dentro del metodo addRowsToPDF() para la lista de facturas del reporte el PDF");
        pdfTable.addCell((String) data.get("nombre"));
        pdfTable.addCell((String) data.get("categoria"));
        pdfTable.addCell((String) data.get("cantidad"));
        pdfTable.addCell(Double.toString((Double) data.get("precio")));
        pdfTable.addCell(Double.toString((Double) data.get("total")));
    }
    private void addTableHeaderPDF(PdfPTable pdfTable) {
        log.info("Dentro del metodo addTableHeaderPDF() para la cabecera del reporte el PDF");
        Stream.of("Nombre", "Categoria", "Cantidad", "Precio", "Sub total")
                .forEach(columnTitle -> {
                    PdfPCell pdfCell = new PdfPCell();
                    pdfCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    pdfCell.setBorderWidth(2);
                    pdfCell.setPhrase(new Phrase(columnTitle));
                    pdfCell.setBackgroundColor(BaseColor.YELLOW);
                    pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(pdfCell);
                });
    }
    private void insertarFactura(Map<String, Object> requestMap) {
        try {
            Factura factura = new Factura();
            factura.setUuid((String) requestMap.get("uuid"));
            factura.setNombre((String) requestMap.get("nombre"));
            factura.setEmail((String) requestMap.get("email"));
            factura.setNumeroContacto((String) requestMap.get("numeroContacto"));
            factura.setMetodoPago((String) requestMap.get("metodoPago"));
            factura.setTotal(Integer.parseInt((String) requestMap.get("montoTotal")));
            factura.setProductoDetalles((String) requestMap.get("productoDetalles"));
            factura.setCreatedBy(jwtFilter.currentUser());
            facturaDAO.save(factura);
        } catch (Exception e) {
            log.error("Error al insertar DATA en la factura", e);
        }
    }
    private boolean validateDataRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("nombre") &&
                requestMap.containsKey("numeroContacto") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("metodoPago") &&
                requestMap.containsKey("productoDetalles") &&
                requestMap.containsKey("montoTotal");
    }
}
