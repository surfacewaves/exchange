import React from 'react'
import { useAuth } from "react-oidc-context"
import { Space, Typography, Button } from 'antd'
const { Text } = Typography

function AuthBar() {
    const auth = useAuth()

    const spaceStyle = {
        background: "lightgrey",
        justifyContent: "end"
    }

    return (
        <Space wrap style={spaceStyle}>
            <Button
                type="primary"
                size="small"
                onClick={() => auth.signoutRedirect()}
                danger
            >
                Logout
            </Button>
        </Space>
    )
}

export default AuthBar