import {Button} from "react-bootstrap";
import {createTheme, CssBaseline, ThemeProvider, Typography} from "@mui/material";
import {red} from "@mui/material/colors";


const theme = createTheme({
    palette: {
        primary: {
            main: '#556cd6',
        },
        secondary: {
            main: '#19857b',
        },
        error: {
            main: red.A400,
        },
    },
});

const Welcome = () => {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Typography variant="h4" component="h1" gutterBottom>
                Test
            </Typography>
            <div className="d-grid gap-2">
                Welcome !
                <Button variant="primary" size="lg">Primary</Button>
            </div>
        </ThemeProvider>
    )
};

export default Welcome;
