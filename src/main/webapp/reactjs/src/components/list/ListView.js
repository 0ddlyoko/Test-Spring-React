import {Table, TableHead} from "@mui/material";


function ListView(props) {
    console.log(props);
    const Header = props.header;
    console.log("AHHHHHH");

    return (
        <Table>
            <TableHead>
                <Header />
            </TableHead>
        </Table>
    );
}

export default ListView;
