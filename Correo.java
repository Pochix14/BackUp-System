import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;




/**
 * Write a description of class Correo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Correo
{
    private String usuario;
    private String contraseña;
    private Session session;

    public Correo()
    {
        usuario = "soporte@sinac.go.cr";
        contraseña = "$upp0rTI*";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.sinac.go.cr");
        props.put("mail.smtp.port", "25");

        session = Session.getInstance(props, new javax.mail.Authenticator()
            {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, contraseña);
                }
            });
    }

    public void enviar(String destinatario, String pdfMalos, String pdfBuenos, String historial, String mensaje){
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinatario));
            message.setSubject("Revisión de Respaldos");            
            BodyPart texto = new MimeBodyPart();
            texto.setText(mensaje);
            MimeMultipart multiParte = new MimeMultipart();
            if (pdfMalos != null)
            {
                BodyPart adjunto = new MimeBodyPart();
                DataSource source = new FileDataSource("\\\\TUCAN\\varios\\1\\Cobian\\" + pdfMalos);
                adjunto.setDataHandler(new DataHandler(source));
                adjunto.setFileName(pdfMalos);
                multiParte.addBodyPart(adjunto);
            }
            if (pdfBuenos != null)
            {
                BodyPart adjunto1 = new MimeBodyPart();
                DataSource source1 = new FileDataSource("\\\\TUCAN\\varios\\1\\Cobian\\" + pdfBuenos);
                adjunto1.setDataHandler(new DataHandler(source1));
                adjunto1.setFileName(pdfBuenos);
                multiParte.addBodyPart(adjunto1);
            }
            if (historial != null)
            {
                BodyPart adjunto2 = new MimeBodyPart();
                DataSource source2 = new FileDataSource("\\\\TUCAN\\varios\\1\\Cobian\\" + historial);
                adjunto2.setDataHandler(new DataHandler(source2));
                adjunto2.setFileName(historial);
                multiParte.addBodyPart(adjunto2);
            }
            
            
            multiParte.addBodyPart(texto);

            message.setContent(multiParte);

            Transport.send(message);
            System.out.println("Su mensaje ha sido enviado");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
