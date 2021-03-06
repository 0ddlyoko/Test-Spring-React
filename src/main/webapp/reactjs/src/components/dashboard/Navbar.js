import { List, ListItemButton, ListItemIcon, ListItemText } from "@mui/material";


function Navbar(props) {
    const nav = Object.keys(props.navs).map((key, idx) => {
        const nav = props.navs[key];
        const onClick = () => props.onClick(key);
        const Icon = nav.icon;
        return (
            <ListItemButton onClick={ onClick } key={ idx }>
                <ListItemIcon>
                    <Icon />
                </ListItemIcon>
                <ListItemText primary={ nav.name }/>
            </ListItemButton>
        );
    })
    return (
        <List component="nav">
            { nav }
        </List>
    );
}

export default Navbar;
