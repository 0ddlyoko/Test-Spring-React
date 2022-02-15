import {
    Box, Container,
    createTheme,
    CssBaseline, Divider,
    IconButton, styled,
    ThemeProvider,
    Toolbar,
    Typography,
} from "@mui/material";
import MuiAppBar from '@mui/material/AppBar';
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import DashboardIcon from "@mui/icons-material/Dashboard";
import MuiDrawer from '@mui/material/Drawer';
import MenuIcon from "@mui/icons-material/Menu";
import {Component} from "react";
import Navbar from "./Navbar";
import Test1 from "./Test1";
import Test2 from "./Test2";
import ProjectList from "../project/ProjectList";

// Theme from https://github.com/mui/material-ui/blob/master/docs/data/material/getting-started/templates/dashboard/

const drawerWidth = 240;

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== "open",
}) (({theme, open}) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const Drawer = styled(MuiDrawer, {
    shouldForwardProp: (prop) => prop !== "open",
}) (({ theme, open }) => ({
    '& .MuiDrawer-paper': {
        position: 'relative',
        whiteSpace: 'nowrap',
        width: drawerWidth,
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
        boxSizing: 'border-box',
        ...(!open && {
            overflowX: 'hidden',
            transition: theme.transitions.create('width', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
            }),
            width: theme.spacing(7),
            [theme.breakpoints.up('sm')]: {
                width: theme.spacing(9),
            },
        }),
    },
}));

const theme = createTheme();

class Dashboard extends Component {
    constructor(props) {
        super(props);
        const nav = this.getNav();
        this.state = {
            open: true,
            nav: nav,
            page: "project",
        };
    }

    toggleDrawer = () => {
        this.setState({open: !this.state.open});
    }

    switchPage = (newPage) => {
        this.setState({page: newPage});
    }

    render() {
        const page = this.state.nav[this.state.page];
        const Page = page["page"];
        return (
            <ThemeProvider theme={ theme }>
                <Box sx={{ display: "flex" }}>
                    <CssBaseline />
                    <AppBar position="absolute" open={ this.state.open }>
                        <Toolbar sx={{
                            pr: "24px",
                        }}>
                            <IconButton
                                edge="start"
                                color="inherit"
                                aria-label="open drawer"
                                onClick={ this.toggleDrawer }
                                sx={{
                                    marginRight: "36px",
                                    ...(this.state.open && {display: "none"}),
                                }}>
                                <MenuIcon />
                            </IconButton>
                            <Typography
                                component="h1"
                                variant="h6"
                                color="inherit"
                                noWrap
                                sx={{flexGrow: 1}}
                            >
                                { page["name"] }
                            </Typography>
                        </Toolbar>
                    </AppBar>
                    <Drawer variant="permanent" open={ this.state.open }>
                        <Toolbar sx={{
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "flex-end",
                            px: [1],
                        }}>
                            <IconButton onClick={ this.toggleDrawer }>
                                <ChevronLeftIcon/>
                            </IconButton>
                        </Toolbar>
                        <Divider/>
                        <Navbar navs={ this.state.nav }/>
                    </Drawer>
                    <Box
                        component="main"
                        sx={{
                            backgroundColor: (theme) =>
                                theme.palette.mode === "light"
                                    ? theme.palette.grey[100]
                                    : theme.palette.grey[900],
                            flexGrow: 1,
                            height: "100vh",
                            overflow: "auto",
                        }}>
                        <Toolbar/>
                        <Container maxWidth="lg" sx={{mt: 4, mb: 4}}>
                            <Page />
                        </Container>
                    </Box>
                </Box>
            </ThemeProvider>
        );
    }

    getNav() {
        // TODO change it
        return {
            "project": {
                "name": "Project",
                "icon": DashboardIcon,
                "onClick": () => this.switchPage("project"),
                "page": ProjectList,
            },
            "test_1": {
                "name": "Test 1",
                "icon": DashboardIcon,
                "onClick": () => this.switchPage("test_1"),
                "page": Test1,
            },
            "test_2": {
                "name": "Test 2",
                "icon": DashboardIcon,
                "onClick": () => this.switchPage("test_2"),
                "page": Test2,
            },
        }
    }
}

export default Dashboard;
